package com.myblog.yu.utils;

import com.myblog.yu.bean.UserInfo;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * 过滤器，防止未经过登录进入其他页面，非法登录
 * @author 容
 * @version 1.0
 * @date 2020/7/17 22:03
 */
@WebFilter("/back/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //获取HttpServletRequest
        HttpServletRequest hrsq = (HttpServletRequest) request;
        String uri = hrsq.getRequestURI();

        //从HttpSession中获取用户的信息userinfo
        HttpSession session = hrsq.getSession();
        UserInfo userinfo = (UserInfo) session.getAttribute("userinfo");

        //如果包含用户登录操作，这个操作且是通过的放行
        if(uri.contains("/back/userLogin")){
            chain.doFilter(request,response);
        }
        //如果session为空跳转登录页面，如果不为空放行
        if (userinfo!=null){
            chain.doFilter(request,response);
        }else {
            hrsq.getRequestDispatcher("/back/login").forward(request,response);
        }
        System.out.println("uri:"+uri);
    }

    @Override
    public void destroy() {

    }
}
