package com.rabbiter.bms.interceptor;

import com.rabbiter.bms.model.User;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.lang.NonNull;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 读者权限拦截器
 * 用于拦截普通用户对管理员功能的访问请求
 */
@Slf4j
public class ReaderInterceptor implements HandlerInterceptor {

    private static final String LOGIN_PAGE = "/index.html";
    
    /**
     * 请求预处理
     * 在Controller处理请求前进行调用
     *
     * @param request 当前HTTP请求
     * @param response HTTP响应
     * @param handler 处理器
     * @return true:继续流程; false:中断流程,不继续向下执行
     */
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request, 
                           @NonNull HttpServletResponse response, 
                           @NonNull Object handler) throws Exception {
        
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("userObj");

        // 检查用户是否存在且是否为管理员
        if (user == null || user.getIsAdmin() == 0) {
            log.warn("非管理员用户尝试访问管理功能: {}", request.getRequestURI());
            // 重定向到登录页面
            response.sendRedirect(request.getContextPath() + LOGIN_PAGE);
            return false;
        }

        return true;
    }
}
