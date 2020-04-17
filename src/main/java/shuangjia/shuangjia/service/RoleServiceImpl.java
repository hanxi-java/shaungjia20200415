package shuangjia.shuangjia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shuangjia.shuangjia.dao.RoleDao;
import shuangjia.shuangjia.entities.Item;
import shuangjia.shuangjia.entities.Module;
import shuangjia.shuangjia.entities.Role;
import shuangjia.shuangjia.entities.UserRoleDuty;
import shuangjia.shuangjia.entities.zEntities.ItemType;

import java.util.List;

/**
*@Author 张典
*@Description 角色业务逻辑层
 *
*/
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;
    @Override
    public int addRole(Role role) {
        /*对比角色是否重复*/
        int result=roleDao.showRoleNum(role);
        if(result==0){
            return roleDao.addRole(role);
        }else {
            return 0;
        }
    }

    @Override
    public List<Role> showRole() {
        List<Role> roleList=roleDao.showRole();
        return roleList;
    }

    @Override
    public int deleteUserRole(int Rid,int num) {
        /*删除角色绑定菜单表*/
        if(num==1){
        int reault1=roleDao.deleteRoleDuty(Rid);
        int reault=roleDao.deleteRole(Rid);;
        return reault;
        }else {
        int reault1=roleDao.deleteRoleDuty(Rid);
        return reault1;
        }
    }

    @Override
    public int updateRole(Role role) {
        /*对比角色是否重复*/
        int result=roleDao.showRoleNum(role);
        if(result==0){
            return roleDao.updateRole(role);
        }else {
            return 0;
        }
    }

    @Override
    public int addRoleDuty(UserRoleDuty userRoleDuty) {
        return roleDao.addRoleDuty(userRoleDuty);
    }

    @Override
    public int deleteSomeRoleDuty(UserRoleDuty userRoleDuty) {
        return roleDao.deleteSomeRoleDuty(userRoleDuty);
    }

    @Override
    public List showRoleDuty(UserRoleDuty userRoleDuty) {
        /*一级菜单*/
        List<Module> modules=roleDao.getModuleByRole(userRoleDuty.getRoleId());
        /*二级普通菜单*/
        for (Module module:modules){
            List<Item> items=roleDao.getItemByRole(userRoleDuty.getRoleId(),module.getModuleId());
                for (Item item:items){
                    /*按钮菜单*/
                    List<ItemType> itemTypes=roleDao.getItemTypeByRole(userRoleDuty.getRoleId(),module.getModuleId(),item.getItemId());
                    item.setChildren(itemTypes);
                }
            module.setChildren(items);
        }
        return modules;
    }

    @Override
    public Role getRoleByType(int roleType) {
        return roleDao.getRoleByType(roleType);
    }
}
