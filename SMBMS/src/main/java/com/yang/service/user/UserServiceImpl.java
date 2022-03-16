package com.yang.service.user;

import com.yang.dao.BaseDao;
import com.yang.dao.user.UserDao;
import com.yang.dao.user.UserDaoImpl;
import com.yang.entity.User;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class UserServiceImpl implements UserService{
//业务层都会调用Dao层接口和实例，需要引入
    private UserDao userDao;
    public  UserServiceImpl (){
        userDao=new UserDaoImpl();
    }
    @Override
    public User login(String userCode, String password) {
        //获得链接
        Connection connection=null;
        //通过方法调用获得user对象，提供给视图层使用
        User user=null;
        try {
            connection=BaseDao.getConnection();
            //调用方法返回user
            user=userDao.getLoginUser(connection,userCode);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeEesoure(connection,null,null);

        }
        return  user;
    }

    //修改密码方法
    @Override
    public boolean updatePwd(int id, String pwd) {
        //获得链接
        Connection connection=null;

        boolean flag=false;
        try {
            connection=BaseDao.getConnection();
            if (userDao.updatePwd(connection,id,pwd)>0){
                flag=true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeEesoure(connection,null,null);
        }
        return flag;
    }

//根据用户名或者用户角色查询用户总数
    @Override
    public int getUserCount(String username, int userRole) {
        //获得链接
        Connection connection=null;
       int count=0;
        try {
            connection=BaseDao.getConnection();
            count = userDao.getUserCount(connection, username, userRole);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            BaseDao.closeEesoure(connection,null,null);
        }
        return count;
    }

    @Override
    public List<User> getUserList(String queryUserName, int queryUserRole, int currentPageNo, int PageSize) {
        Connection connection=null;
        List<User> userList=null;
        connection=BaseDao.getConnection();
        try {
           userList= userDao.getUserList(connection,queryUserName,queryUserRole,currentPageNo,PageSize);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeEesoure(connection,null,null);
        }
        return userList;
    }

    @Override
    public boolean addUser(User user) {
      boolean flag=false;
      Connection connection=null;
      connection=BaseDao.getConnection();
        try {
            connection.setAutoCommit(false);//开启jdbc事务管理
            int updateRows=userDao.addUser(connection,user);
            connection.commit();//提交事务
            if (updateRows>0){
                flag=true;
                System.out.println("提交事务成功");
            }else {
                System.out.println("提交事务失败");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            //出现异常，事务提交失败
            System.out.println("rollback>>>>异常，进行事务回滚");
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }finally {
            BaseDao.closeEesoure(connection,null,null);
        }
        return flag;
    }




    //测试方法，需要引入junit
    @Test
    public void  test(){
        UserServiceImpl userService=new UserServiceImpl();
        User user = userService.login("admin", "12");
        System.out.println(user.getUserPassword());
    }
    @Test
    public  void TestGetUserCount(){
        UserService userService=new UserServiceImpl();
        int userCount = userService.getUserCount(null, 0);
        System.out.println(userCount);
    }
    @Test
    public  void TestgetUserList(){
        Date date = new Date();
        date.getTime();
        UserServiceImpl userService=new UserServiceImpl();

        List<User> userList = userService.getUserList("", 0, 1, 12);

        for (User u: userList) {
            System.out.println(date.getYear()-u.getBirthday().getYear());
        }
    }
}
