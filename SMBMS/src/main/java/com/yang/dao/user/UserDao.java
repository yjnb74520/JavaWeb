package com.yang.dao.user;

import com.yang.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface UserDao {
    //获取用户登录信息，connection作为数据库链接对象
    public User getLoginUser(Connection connection,String userCode) throws SQLException;

    //修改密码
    public int updatePwd(Connection connection, int id, String pwd)throws SQLException;

    //根据用户名或者用户角色查询用户总数
    public int getUserCount(Connection connection,String username,int userRole)throws SQLException;

    //通过条件查询userlist
    public List<User> getUserList(Connection connection, String username, int userRole, int currentPageNo, int pageSize)throws SQLException;

    //新增用户
    public  int addUser (Connection connection,User user) throws SQLException;
}
