package com.yang.service.role;

import com.yang.dao.BaseDao;
import com.yang.dao.role.RoleDao;
import com.yang.dao.role.RoleDaoImpl;
import com.yang.entity.Role;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoleServiceImpl implements RoleService{
  private   RoleDao roleDao;

    public RoleServiceImpl() {
       roleDao=new RoleDaoImpl();
    }

    @Override
    public List<Role> getRoleList() {
        Connection connection=null;
       List<Role> roleList = null;

        try {
            connection=BaseDao.getConnection();
            roleList=roleDao.getRoleList(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            BaseDao.closeEesoure(connection,null,null);
        }
        return roleList;

    }
    @Test
    public void RoleTest(){

        RoleServiceImpl roleService = new RoleServiceImpl();
        List<Role> roleList = roleService.getRoleList();
        for (Role role: roleList) {
            System.out.println(role.getRoleCode());
        }
    }
}
