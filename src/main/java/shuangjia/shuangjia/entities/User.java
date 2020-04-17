package shuangjia.shuangjia.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;

/**
*@Author 张典
*@Description User实体类
 *
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    /*用户ID*/
    private  int id;
    //登录名
    private  String userName;
    //密码
    private  String psw;
    //名字
    private  String name;
    private  String tele;
    private  String email;
    private  int adDeptId;
    private  String weixinId;
    private  String remark;
    //状态
    private  String state;

    /*用户权限*/
    private  Role role;
}
