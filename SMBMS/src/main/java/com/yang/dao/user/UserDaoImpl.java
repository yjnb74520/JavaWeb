package com.yang.dao.user;

import com.mysql.cj.util.StringUtils;
import com.yang.dao.BaseDao;
import com.yang.entity.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserDaoImpl implements  UserDao{
    @Override
    public User getLoginUser(Connection connection, String userCode) throws SQLException {
        PreparedStatement preparedStatement =null;
        ResultSet resultSet=null;
        User user=null;
        if (connection!=null){//判断数据库是否链接，如果链接成功则：
            String sql="select  * from smbms_user where userCode=?";
            Object [] params={userCode};//设置参数
            //调用查询方法
            resultSet= BaseDao.execute(connection,preparedStatement,resultSet,sql,params);
            if (resultSet.next()){
                //获取查询结果
               user=new User();
                user.setId(resultSet.getInt("id"));
                user.setUserCode(resultSet.getString("userCode"));
                user.setUserName(resultSet.getString("userName"));
                user.setUserPassword(resultSet.getString("userPassword"));
                user.setGender(resultSet.getInt("gender"));
                user.setBirthday(resultSet.getDate("birthday"));
                user.setPhone(resultSet.getString("phone"));
                user.setAddress(resultSet.getString("address"));
                user.setUserRole(resultSet.getInt("userRole"));
                user.setCreatedBy(resultSet.getInt("createdBy"));
                user.setCreationDate(resultSet.getDate("creationDate"));
                user.setModifyBy(resultSet.getInt("modifyBy"));
                user.setModifyDate(resultSet.getDate("modifyDate"));

            }
            //关闭链接
            BaseDao.closeEesoure(null,preparedStatement,resultSet);
        }
        //返回结果
        return user;
    }

    //修改密码方法
    @Override
    public int updatePwd(Connection connection, int id, String pwd) throws SQLException {
        PreparedStatement preparedStatement=null;
        int execute=0;
        if (connection!=null){
            String sql="update  smbms_user set  userPassword=? where id=?";
            Object [] params={pwd,id};
            execute = BaseDao.execute(connection, preparedStatement, sql, params);
            //Service层再去关闭链接
            BaseDao.closeEesoure(null,preparedStatement,null);
        }
        return execute;
    }

    //根据用户名或者用户角色查询用户总数
    //根据前端不同的参数，拼接不同的sql语句
    @Override
    public int getUserCount(Connection connection, String username, int userRole) throws SQLException {
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        int count=0;//查询的人数，提到全局里，最后返回结果
        if (connection!=null){
            StringBuffer sql=new StringBuffer();//定义StringBuffer类型，方便进行字符串拼接sql语句
            //count(1)表示查询所有数据，也可也用count(*)不推荐，效率慢
            sql.append("select count(1) as count from smbms_user u,smbms_role r where u.userRole=r.id");
            ArrayList<Object> list = new ArrayList<Object>();//存放参数
            if (!StringUtils.isNullOrEmpty(username)){//如果输入的参数不为空，在sql语句后面进行增加语句
                sql.append(" and u.userName like ?");//注意空格
                list.add("%"+username+"%");//index=0,模糊查询。
            }
            if (userRole>0){
                sql.append(" and u.userRole=?");//注意空格

                list.add(userRole);//index=1
            }
            //转换为数组
            Object[] params = list.toArray();
            System.out.println("userDaoImpl完整的sql语句"+sql.toString());
            resultSet= BaseDao.execute(connection, preparedStatement, resultSet, sql.toString(), params);
            if (resultSet.next()){
                //从结果集中获取最终的数量count
                count=resultSet.getInt("count");
            }
            BaseDao.closeEesoure(null,preparedStatement,resultSet);
        }
        return count;
    }

    //通过条件查询userlist
    @Override
    public List<User> getUserList(Connection connection, String username, int userRole, int currentPageNo, int pageSize) throws SQLException {
        PreparedStatement preparedStatement=null;
        ResultSet resultSet=null;
        List<User> userList=new ArrayList<>();//这个用于最后传递数据

        int count=0;//查询的人数，提到全局里，最后返回结果
        if (connection!=null){
            StringBuffer sql=new StringBuffer();//定义StringBuffer类型，方便进行字符串拼接sql语句
            //count(1)表示查询所有数据，也可也用count(*)不推荐，效率慢
            sql.append("select u.*,r.roleName as userRoleName from smbms_user u,smbms_role r where u.userRole = r.id");
            List<Object> list=new ArrayList<>();
            if (!StringUtils.isNullOrEmpty(username)){//如果输入的参数不为空，在sql语句后面进行增加语句
                sql.append(" and u.userName like ?");//注意空格
                list.add("%"+username+"%");//index=0,模糊查询。
            }
            if (userRole>0){
                sql.append(" and u.userRole=?");//注意空格

                list.add(userRole);//index=1
            }

            //分页展示，在数据库中，分页展示 limit startIndex pageSize；总数
            //当前页=（当前页-1）*页面大小
            sql.append(" order by  creationDate DESC  limit ?,?");
            currentPageNo=(currentPageNo-1)*pageSize;
            list.add(currentPageNo);
            list.add(pageSize);
            //转换为数组
            Object[] params = list.toArray();
            System.out.println("userDaoImpl完整的sql语句"+sql.toString());
            resultSet = BaseDao.execute(connection, preparedStatement, resultSet, sql.toString(), params);
            while (resultSet.next()){
                User user=new User();
                Date date = new Date();
                user.setId(resultSet.getInt("id"));
                user.setUserCode(resultSet.getString("userCode"));
                user.setUserName(resultSet.getString("userName"));

                user.setGender(resultSet.getInt("gender"));
                java.util.Date birthday = resultSet.getDate("birthday");
//                user.setBirthday(date.getYear()-Math.toIntExact(birthday.getYear())birthday.getYear());
                user.setBirthday(resultSet.getDate("birthday"));
                user.setPhone(resultSet.getString("phone"));

                user.setUserRole(resultSet.getInt("userRole"));
               user.setUserRoleName(resultSet.getString("userRoleName"));
                user.setAge(date.getYear()-resultSet.getDate("birthday").getDate() );
               userList.add(user);

            }

            BaseDao.closeEesoure(null,preparedStatement,resultSet);
        }
        return userList;
    }

    @Override
    public int addUser(Connection connection, User user) throws SQLException {
      PreparedStatement preparedStatement=null;
      int updateRows=0;
      if (connection!=null){
          String sql="insert into smbms_user(userCode, userName, userPassword, gender," +
                  " birthday, phone, address, userRole, createdBy, creationDate) " +
                  "VALUES (?,?,?,?,?,?,?,?,?,?)";
          Object [] params={user.getUserCode(),user.getUserName(),user.getUserPassword()
          ,user.getGender(),user.getBirthday(),user.getPhone(),user.getAddress(),user.getUserRole()
          ,user.getCreatedBy(),user.getCreationDate()};
          updateRows=BaseDao.execute(connection,preparedStatement,sql,params);
      }
      return updateRows;
    }


}
