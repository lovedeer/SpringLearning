package com.smart.processor;

import com.smart.domain.Leader;
import com.smart.domain.Leaderdelegate;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println(bean.getClass().getName());
        if (beanName.equals("leader2")) {
            Leader leader = (Leader)((ProxyFactoryBean)bean).getObject();
            Leaderdelegate leaderdelegate = new Leaderdelegate(leader);
            leaderdelegate.service();
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
