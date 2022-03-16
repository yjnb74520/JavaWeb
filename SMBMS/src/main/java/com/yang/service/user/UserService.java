package com.yang.service.user;

import com.yang.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserService {

    public User login(String userCode,String password);

    //修改密码
    public boolean updatePwd( int id, String pwd);

    //根据用户名或者用户角色查询用户总数
    public int getUserCount(String username,int userRole );
    //根据条件查询用户列表,参数当前页，当前页大小
    public List<User> getUserList(String queryUserName,int queryUserRole, int currentPageNo,int PageSize);
    public  boolean addUser(User user);
}
