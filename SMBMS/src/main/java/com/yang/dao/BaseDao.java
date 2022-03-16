package com.yang.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

//操作数据库的公共类
public class BaseDao {
    private static  String driver;
    private static  String url;
    private static  String username;
    private static  String password;

    //静态代码块，类加载的时候就初始化
    static  {
        Properties properties=new Properties();
        //通过类加载器读取对应的资源
        InputStream is = BaseDao.class.getClassLoader().getResourceAsStream("db.properties");
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }

        driver=properties.getProperty("driver");
        url=properties.getProperty("url");
        username=properties.getProperty("username");
        password=properties.getProperty("password");
    }


    //获取数据库链接
    public  static Connection getConnection(){
        Connection connection=null;
        try {
            Class.forName(driver);
            connection= DriverManager.getConnection(url,username,password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return connection;
    }


    //编写查询公共类

    public static ResultSet execute(Connection connection,  PreparedStatement preparedStatement,ResultSet resultSet,String sql, Object[] params ) throws SQLException {
       //先预编译sql，后面直接就可以执行
       //这里传入Object类型的数组，是为了在查询的时候我们不知到查询的参数有多少，所以这里用数组来替代

            preparedStatement=connection.prepareStatement(sql);
            for (int i = 0; i < params.length; i++) {
                //这里就是设置sql语句的参数，使用for循环，把参数设置进去，因为preparedStatement从1开始，所有i+1
                preparedStatement.setObject(i+1,params[i]);
            }
            //执行sql，然后返回结果集。
         resultSet = preparedStatement.executeQuery();
        return resultSet;
    }

    //编写增删改公共方法，和查询差不多，所以使用方法重载，增删改只需要返回成功或者失败，因此不需要resultset
    public static int execute(Connection connection,PreparedStatement preparedStatement, String sql, Object[] params) throws SQLException {
        //先预编译sql，后面直接就可以执行
        //这里传入Object类型的数组，是为了在查询的时候我们不知到查询的参数有多少，所以这里用数组来替代

        preparedStatement=connection.prepareStatement(sql);
        for (int i = 0; i < params.length; i++) {
            //这里就是设置sql语句的参数，使用for循环，把参数设置进去，因为preparedStatement从1开始，所有i+1
            preparedStatement.setObject(i+1,params[i]);
        }
        //执行sql，然后返回int类型的数字（受影响行数）
        int updateRows = preparedStatement.executeUpdate();
        return updateRows;
    }

    //释放资源
    public  static  boolean closeEesoure(Connection connection,PreparedStatement preparedStatement,ResultSet resultSet){
        boolean flag=true;
        if (connection!=null){
            try {
                connection.close();
                //GC垃圾回收
                connection=null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag=false;
            }
        }
        if (preparedStatement!=null){
            try {
                preparedStatement.close();
                //GC垃圾回收
                preparedStatement=null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag=false;
            }
        }
        if (resultSet!=null){
            try {
                resultSet.close();
                //GC垃圾回收
                resultSet=null;
            } catch (SQLException e) {
                e.printStackTrace();
                flag=false;
            }
        }
return flag;
    }
}
