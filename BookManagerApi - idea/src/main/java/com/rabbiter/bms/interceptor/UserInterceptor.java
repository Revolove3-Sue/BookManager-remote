package com.rabbiter.bms.interceptor;

import com.rabbiter.bms.model.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.lang.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 用户登录拦截器
 * 用于拦截未登录用户的访问请求
 */
@Slf4j
public class UserInterceptor implements HandlerInterceptor {

    private static final String LOGIN_PAGE = "/index.html";
    private static final String USER_SESSION_KEY = "userObj";
    
    /**
     * 请求预处理
     * 在Controller处理请求前进行调用，验证用户是否已登录
     *
     * @param request 当前HTTP请求
     * @param response HTTP响应
     * @param handler 处理器
     * @return true:继续流程; false:中断流程,重定向到登录页面
     */
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, 
                           @NonNull HttpServletResponse response, 
                           @NonNull Object handler) throws Exception {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER_SESSION_KEY);

        // 检查用户是否已登录
        if (user == null) {
            log.warn("未登录用户尝试访问受保护资源: {}", request.getRequestURI());
            // 重定向到登录页面
            response.sendRedirect(request.getContextPath() + LOGIN_PAGE);
            return false;
        }

        return true;
    }
}