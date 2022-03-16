package com.yang.servlet.user;

import com.yang.entity.User;
import com.yang.service.user.UserServiceImpl;
import com.yang.until.Constant;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginUserServlet extends HttpServlet {
//Servlet控制层，调用业务层代码
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获得用户名和密码
        String userCode = req.getParameter("userCode");
        String userPassword = req.getParameter("userPassword");
        //和数据库进行比对，调用业务层代码
        UserServiceImpl userService=new UserServiceImpl();
        User user = userService.login(userCode, userPassword);//通过输入的账号和密码查询信息，获得了查询结果
        if (user!=null&&userCode.equals(user.getUserCode())&&userPassword.equals(user.getUserPassword())){
                //将用户放到Session中，方便在其他页面也能取到用户信息
                req.getSession().setAttribute(Constant.USER_SESSION, user);
                //重定向跳转到登录成功后的界面
                resp.sendRedirect("jsp/frame.jsp");
        }else {//没有查到这个人，转发回到登录界面，并提示错误
            req.setAttribute("error","账号或密码错误");
            req.getRequestDispatcher("/login.jsp").forward(req,resp);


        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }
}
