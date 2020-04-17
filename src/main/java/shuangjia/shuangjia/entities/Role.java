package shuangjia.shuangjia.entities;

import lombok.Data;
/**
*@Author 张典
*@Description 用户权限类
 *
*/

@Data
public class Role {
    /*角色id*/
    private int rid;
    /*角色类型 1:超级管理员 2:管理员 3:公司领导*/
    private int roleType;
    /*名称*/
    private String roleName;
    /*备注*/
    private String roleRemark;
}
