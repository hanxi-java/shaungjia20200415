package shuangjia.shuangjia.service;

import shuangjia.shuangjia.entities.Role;
import shuangjia.shuangjia.entities.UserRoleDuty;

import java.util.List;

/**
*@Author 张典
*@Description 角色业务接口
 *
*/
public interface RoleService {
        int addRole(Role role);
        List<Role> showRole();
        int deleteUserRole(int Rid,int num);
        int updateRole(Role role);
        int addRoleDuty(UserRoleDuty userRoleDuty);
        int deleteSomeRoleDuty(UserRoleDuty userRoleDuty);
        List showRoleDuty(UserRoleDuty userRoleDuty);
        Role getRoleByType(int roleType);
       }
