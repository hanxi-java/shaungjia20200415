package shuangjia.shuangjia.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import shuangjia.shuangjia.entities.Item;
import shuangjia.shuangjia.entities.Module;
import shuangjia.shuangjia.entities.Role;
import shuangjia.shuangjia.entities.UserRoleDuty;
import shuangjia.shuangjia.entities.zEntities.ItemType;

import java.util.List;

/**
*@Author 张典
*@Description 角色数据访问
 *
*/
@Mapper
public interface RoleDao {
        /*添加角色*/
        int addRole(Role role);
        /*修改角色信息*/
        int updateRole(Role role);
        /*角色信息展示*/
        List<Role> showRole();
        /*通过roletype查找角色*/
        Role getRoleByType(@Param("roleType") int roleType);
        /*获取按条件查询角色条数*/
        int showRoleNum(Role role);
        /*删除角色*/
        int deleteRole(@Param("Rid") int Rid);
        /*清空角色菜单表*/
        int deleteRoleDuty(@Param("Rid") int Rid);
        /*添加角色绑定菜单*/
        int addRoleDuty(UserRoleDuty userRoleDuty);
        /*删除角色菜单表*/
        int deleteSomeRoleDuty(UserRoleDuty userRoleDuty);
        /*角色一级菜单*/
        List<Module> getModuleByRole(@Param("roleId") int roleId);
        /*角色二级菜单*/
        List<Item> getItemByRole(@Param("roleId") int roleId,@Param("groupId") int groupId);
        /*角色二级按钮菜单*/
        List<ItemType> getItemTypeByRole(@Param("roleId") int roleId,
                                         @Param("groupId") int groupId,
                                         @Param("parentId") int parentId);
}
