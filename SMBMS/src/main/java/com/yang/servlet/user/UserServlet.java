package com.yang.servlet.user;

import com.alibaba.fastjson.JSONArray;
import com.mysql.cj.util.StringUtils;
import com.yang.entity.Role;
import com.yang.entity.User;
import com.yang.service.role.RoleServiceImpl;
import com.yang.service.user.UserService;
import com.yang.service.user.UserServiceImpl;
import com.yang.until.Constant;
import com.yang.until.PageSupport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.*;

public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //实现方法复用
        String method = req.getParameter("method");
        String pageIndex=req.getParameter("pageIndex");
        if (method.equals("savepwd")  && method != null) {
            this.updatePwd(req, resp);
        }else if(method.equals("pwdmodify")&&method!=null){
            this.pwdModfy(req,resp);
        }else if(method.equals("query")&&method!=null){
            this.query(req,resp);
        }else  if (pageIndex.equals("1")&&pageIndex!=null){
            this.userAdd(req,resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req, resp);
    }

    //修改密码
    public  void    updatePwd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        //从Session中拿id
        User user = (User) req.getSession().getAttribute(Constant.USER_SESSION);
        Integer id = user.getId();
        String oldUserPassword = user.getUserPassword();//旧密码，从session中取到的
        String scoldpwd = req.getParameter(Constant.OLDPASSORD);//获得输入的旧密码
//        String scnewpwd = req.getParameter(Constant.NEWPASSWORD);//获得输入的新密码
        String scnewpwd = req.getParameter("newpassword");
        String scrnewpwd = req.getParameter(Constant.RNEWPASSWORD);//获得输入的确认新密码
        boolean flage=false;

        if (user!=null&&scnewpwd!=null){
            UserService userService=new UserServiceImpl();
            flage = userService.updatePwd(id, scnewpwd);
            if (flage){
                req.setAttribute("message","密码修改成功，请退出重新登录");
                //密码修改成功移除Session，重定向,因为有过滤器Session不存在自动跳转
                req.getSession().removeAttribute(Constant.USER_SESSION);
            }else {
                req.setAttribute("message","密码修改失败");
            }

        }else {
            req.setAttribute("message","你的旧密码或者新输入的密码不对，请重试");
        }
        //不管修改成功或者失败都跳转到本页

            req.getRequestDispatcher("pwdmodify.jsp").forward(req,resp);

    }
//验证旧密码
    public  void pwdModfy(HttpServletRequest req, HttpServletResponse resp){
        User user = (User) req.getSession().getAttribute(Constant.USER_SESSION);

        String scoldpwd = req.getParameter(Constant.OLDPASSORD);//获得输入的旧密码
        Map<String, String> resultMap = new HashMap<>();
        if (user==null){//session过期
            resultMap.put("result","sessionerror");
          }else if (StringUtils.isNullOrEmpty(scoldpwd)){
            resultMap.put("result","error");
        }else {
            String oldUserPassword = user.getUserPassword();//旧密码，从session中取到的
            if (oldUserPassword.equals(scoldpwd)){
                resultMap.put("result","true");
            }else{
                resultMap.put("result","false");
            }
        }
        try {
            resp.setContentType("application/json");
            PrintWriter writer = resp.getWriter();
            writer.write(JSONArray.toJSONString(resultMap));
            writer.flush();
            writer.close();
        }catch (Exception e){
        }
    }
    //重点、难点
    private void query(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
       //从前端获取数据
        String queryUserName = req.getParameter("queryname");
        String temp = req.getParameter("queryUserRole");//临时变量，这里只前端的下拉框
        String pageIndex = req.getParameter("pageIndex");//前端传过来的页码
        int queryUserRole=0;//这给0是为了保障前端在下选择框的时候，有默认值

//设置页码，第一次访问一定是第一页
        int pageSize=5;//一页显示多少个
        int currentPageNo=1;

        if (queryUserName==null){
            queryUserName="";
        }
        if (temp!=null&&!temp.equals("")){
            queryUserRole=Integer.parseInt(temp);//如果下拉框不等于空，就有值，给下拉选项框赋值。
        }
        if (pageIndex!=null){
            try {
                currentPageNo=Integer.parseInt(pageIndex);//整形转换
            }catch (Exception e){
                resp.sendRedirect("error.jsp");
            }
        }


        UserServiceImpl userService = new UserServiceImpl();
        //获取总人数
        int totalCount= userService.getUserCount(queryUserName,queryUserRole);

        //使用工具类PageSupport获得分页，需要设置参数
        PageSupport pageSupport = new PageSupport();
        //当前页码-来自于用户输入
        pageSupport.setCurrentPageNo(currentPageNo);
        //页面容量
        pageSupport.setPageSize(pageSize);
        //总数量（表）
        pageSupport.setTotalCount(totalCount);

        //控制首页和尾页，避免超出页数
        int totalPageCount = pageSupport.getTotalPageCount();
        if (currentPageNo<1){//小于1，就等于第一页
            currentPageNo=1;
        }else  if (currentPageNo>totalPageCount){//大于最后一页就等于最后一页
            currentPageNo=totalPageCount;
        }
        //获取用户列表展示
        List<User> userList = userService.getUserList(queryUserName, queryUserRole, currentPageNo, pageSize);
/*
<c:forEach var="user" items="${userList }" varStatus="status">
* 这里定义的变量名来自于前端设置的，userList
因为前端回去便利读取在这些数据，所有需要存入，时效性很短，不需要设置到Session中
* */
        req.setAttribute("userList",userList);

        //还需要角色列表
        RoleServiceImpl roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();
        /*
        *   <c:forEach var="role" items="${roleList}">
        同理这里需要注意命名和前端一样，因为前端回去便利读取在这些数据，所有需要存入，时效性很短，不需要设置到Session中
        * */
        req.setAttribute("roleList",roleList) ;

        /*前端页面还需要参数
        * 	<c:import url="rollpage.jsp">
	          	<c:param name="totalCount" value="${totalCount}"/>
	          	<c:param name="currentPageNo" value="${currentPageNo}"/>
	          	<c:param name="totalPageCount" value="${totalPageCount}"/>
          	</c:import>
        * */
        req.setAttribute("totalCount",totalCount);
        req.setAttribute("currentPageNo",currentPageNo);
        req.setAttribute("totalPageCount",totalPageCount);

        //返回前端
        req.getRequestDispatcher("userlist.jsp").forward(req,resp);

    }

    //新增用户
    /*把前端输入的参数拿到，一共有8项目，其中密码分新旧密码，需要判断
    * 新增到数据库却需要12个数据，对前端没拿到的数据进行创造
    * */
    public void  userAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect("useradd.jsp");
        //前端获取数据
        String userCode=req.getParameter("userCode");
        String userName=req.getParameter("userName");
        String userPassword=req.getParameter("userPassword");
        String ruserPassword=req.getParameter("ruserPassword");
        String gender=req.getParameter("gender");
        String birthday=req.getParameter("birthday");
        String phone=req.getParameter("phone");
        String address=req.getParameter("address");
        String userRole=req.getParameter("userRole");


        //设置新增用户对象内容
        User user=new User();
        user.setUserCode(userCode);
        user.setUserPassword(userPassword);
        user.setUserName(userName);
        user.setAddress(address);
        try {
            user.setBirthday((Date) new SimpleDateFormat("yyy-MM-dd").parse(birthday));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        user.setGender(Integer.valueOf(gender));
        user.setPhone(phone);
        user.setUserRole(Integer.valueOf(userRole));
        user.setCreationDate(new java.util.Date());
        User gtId = (User) req.getSession().getAttribute(Constant.USER_SESSION);
        UserServiceImpl userService = new UserServiceImpl();
        user.setCreatedBy(gtId.getId());
        if (userPassword.equals(ruserPassword)){
            System.out.println("新旧密码一致");
            if (userService.addUser(user)){
                resp.sendRedirect(req.getContextPath()+"/jsp/user.do?method=query");
            }else {
                req.getRequestDispatcher("useradd.jsp").forward(req,resp);
            }
        }
    }

    }


