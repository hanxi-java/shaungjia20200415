package shuangjia.shuangjia.entities;

import lombok.Data;

/**
*@Author 张典
*@Description 角色权限类
 *
*/
@Data
public class UserRoleDuty {
    private int DId;
    /*角色ID*/
    private int roleId;
    /*角色对应菜单ID*/
    private int itemId ;
    /*备注*/
    private String dutyRemark;

    /*角色信息*/
    private Role role;
    /*菜单信息*/
    private Item item;
    /*菜单信息数组*/
    private Item[] items;
}
