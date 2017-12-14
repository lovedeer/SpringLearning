package com.smart.plugin;

import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.Statement;
import java.util.*;

@Intercepts(value = {
        @Signature(type = StatementHandler.class, method = "query", args = {Statement.class, ResultHandler.class}),
        @Signature(type = StatementHandler.class, method = "prepare", args = {Connection.class, Integer.class})})
public class PageInterceptor implements Interceptor {
    private Properties props;
    private static Set<String> methodCache = Collections.synchronizedSet(new HashSet<String>());

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        StatementHandler st = getStatementHandler(invocation);
        MappedStatement ms = getMappedStatement(st);
        String method = invocation.getMethod().getName();
//        if (method.equals("query")) {
//            return queryIntercept(invocation, st, ms);
//        }
        if (method.equals("prepare"))
            return prepareIntercept(invocation, st, ms);
        return invocation.proceed();
    }

//    private Object queryIntercept(Invocation invocation, StatementHandler st, MappedStatement ms) throws Throwable {
//        String methodFilter = props.getProperty("method");
//
//        if (ms != null && ms.getId().contains(methodFilter)) {
//            BoundSql boundSql = st.getBoundSql();
//            String sql = boundSql.getSql();
//            int limitIndex = sql.indexOf("limit");
//            sql = sql.substring(0, limitIndex > 0 ? limitIndex : sql.length() - 1);
//            sql += " limit ?,?";
//            Field sqlField = BoundSql.class.getDeclaredField("sql");
//            if (!sqlField.isAccessible())
//                sqlField.setAccessible(true);
//            sqlField.set(boundSql, sql);
//        }
//        return invocation.proceed();
//    }

    private Object prepareIntercept(Invocation invocation, StatementHandler st, MappedStatement ms) throws Throwable {
        String methodFilter = props.getProperty("method");

        if (ms != null && ms.getId().contains(methodFilter)) {
            BoundSql boundSql = st.getBoundSql();
            String sql = boundSql.getSql();
            int limitIndex = sql.indexOf("limit") > 0 ? sql.indexOf("limit") : sql.indexOf("LIMIT");
            sql = sql.substring(0, limitIndex > 0 ? limitIndex : sql.length());
            sql += " limit ?,?";
            Field sqlField = BoundSql.class.getDeclaredField("sql");
            if (!sqlField.isAccessible())
                sqlField.setAccessible(true);
            sqlField.set(boundSql, sql);

            if (methodCache.add(ms.getId())) {
                ParameterMapping.Builder builder = new ParameterMapping.Builder(ms.getConfiguration(), "start", Integer.class);
                ParameterMapping startMap = builder.build();
                List parameterMappings = boundSql.getParameterMappings();
                Field listField = parameterMappings.getClass().getSuperclass().getDeclaredField("list");
                if (!listField.isAccessible())
                    listField.setAccessible(true);
                List list = (List) listField.get(parameterMappings);

                list.add(startMap);
                builder = new ParameterMapping.Builder(ms.getConfiguration(), "pageSize", Integer.class);
                ParameterMapping pageSizeMap = builder.build();
                list.add(pageSizeMap);
                listField.set(parameterMappings, list);
            }
        }
        return invocation.proceed();
    }

    private StatementHandler getStatementHandler(Invocation invocation) throws Throwable {
        RoutingStatementHandler rst = (RoutingStatementHandler) invocation.getTarget();
        Field delegate = RoutingStatementHandler.class.getDeclaredField("delegate");
        if (!delegate.isAccessible())
            delegate.setAccessible(true);
        return (StatementHandler) delegate.get(rst);

    }

    private MappedStatement getMappedStatement(StatementHandler st) throws Throwable {
        Field field = BaseStatementHandler.class.getDeclaredField("mappedStatement");
        if (!field.isAccessible())
            field.setAccessible(true);
        return (MappedStatement) field.get(st);
    }


    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        this.props = properties;
    }
}
