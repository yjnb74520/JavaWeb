package com.yang.filter;

import com.yang.entity.User;
import com.yang.until.Constant;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

    public class SysFilter implements Filter {
        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
    //        Filter.super.init(filterConfig);
        }

        @Override
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
            HttpServletRequest req = (HttpServletRequest) servletRequest;
            HttpServletResponse resp = (HttpServletResponse) servletResponse;
            User user = (User) req.getSession().getAttribute(Constant.USER_SESSION);
            if (user==null){
                resp.sendRedirect("/SMBMS/error.jsp");
            }
                filterChain.doFilter(servletRequest,servletResponse);
        }

        @Override
        public void destroy() {
    //        Filter.super.destroy();
        }
    }
