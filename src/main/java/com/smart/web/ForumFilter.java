package com.smart.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import static com.smart.cons.CommonConstant.*;

import com.smart.domain.User;

public class ForumFilter implements Filter {
    private static final String FILTERED_REQUEST = "@@session_context_filtered_request";

    // ① 不需要登录即可访问的URI资源
    private static final String[] INHERENT_ESCAPE_URIS = {"/login.html"};

    // ② 执行过滤
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        String reqUrl = httpRequest.getRequestURL().toString();
        if (reqUrl.endsWith(".css") || reqUrl.endsWith(".js") || reqUrl.endsWith(".jpg") || reqUrl.endsWith(".bmp") || reqUrl.endsWith(".jif") || reqUrl.endsWith(".png")
                || reqUrl.contains("/login/")) {
            chain.doFilter(request, response);
            return;
        }
        // ②-1 保证该过滤器在一次请求中只被调用一次
        if (httpRequest != null && httpRequest.getAttribute(FILTERED_REQUEST) != null) {
            chain.doFilter(httpRequest, response);
        } else {

            // ②-2 设置过滤标识，防止一次请求多次过滤
            httpRequest.setAttribute(FILTERED_REQUEST, Boolean.TRUE);
            User userContext = getSessionUser(httpRequest);

            // ②-3 用户未登录, 且当前URI资源需要登录才能访问
            if (userContext == null
                    && !isURILogin(httpRequest.getRequestURI(), httpRequest)) {
                String toUrl = httpRequest.getRequestURL().toString();
                if (!StringUtils.isEmpty(httpRequest.getQueryString())) {
                    toUrl += "?" + httpRequest.getQueryString();
                }

                // ②-4将用户的请求URL保存在session中，用于登录成功之后，跳到目标URL
                httpRequest.getSession().setAttribute(LOGIN_TO_URL, toUrl);

                // ②-5转发到登录页面
                String url = httpRequest.getContextPath();
                httpResponse.sendRedirect(url + "/login.html");
                return;
            }
            chain.doFilter(request, response);
        }
    }

    public void init(FilterConfig filterConfig) throws ServletException {
    }

    /**
     * 当前URI资源是否需要登录才能访问
     *
     * @param requestURI
     * @param request
     * @return
     */
    private boolean isURILogin(String requestURI, HttpServletRequest request) {
        if (request.getContextPath().equalsIgnoreCase(requestURI)
                || (request.getContextPath() + "/").equalsIgnoreCase(requestURI))
            return true;
        for (String uri : INHERENT_ESCAPE_URIS) {
            if (requestURI != null && requestURI.indexOf(uri) >= 0) {
                return true;
            }
        }
        return false;
    }

    protected User getSessionUser(HttpServletRequest request) {
        return (User) request.getSession().getAttribute(USER_CONTEXT);
    }

    public void destroy() {
    }
}
