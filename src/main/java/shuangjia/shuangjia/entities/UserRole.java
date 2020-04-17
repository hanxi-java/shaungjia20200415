package shuangjia.shuangjia.entities;

import lombok.Data;

/**
*@Author 张典
*@Description 用户角色类
 *
*/
@Data
public class UserRole {
    private int URId;
    /*用户ID*/
    private int userId;
    /*角色ID*/
    private int roleId;
    /*用户信息*/
    private User user;
    /*角色信息*/
    private Role role;
    /*页码*/
    private Paging paging;
    /*判断角色操作*/
    private int num;
    /*角色数组*/
    private Role[] roles;
}
