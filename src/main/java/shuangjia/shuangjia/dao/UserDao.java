package shuangjia.shuangjia.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import shuangjia.shuangjia.entities.Role;
import shuangjia.shuangjia.entities.User;
import shuangjia.shuangjia.entities.UserDepart;
import shuangjia.shuangjia.entities.UserRole;

import java.util.List;

/**
 * @Author 张典
 * @Description 用户数据访问
 */
@Mapper
public interface UserDao {

        /*登录验证用户信息*/
        User loginRequired(@Param("userName") String userName, @Param("psw") String psw);

        /*通过登录名查找用户信息*/
        User getUserInfoByUserName(@Param("userName") String userName);

        /*用户信息列表*/
        List<User> getAllUserInfo(@Param("index") int index, @Param("pageSize") int pageSize);

        /*修改密码*/
        int updatePwd(@Param("userName") String userName, @Param("psw") String psw);

        /*修改用户基本信息*/
        int updateUserInfo(User user);

        /*增加用户信息*/
        int addUser(User user);

        /*删除用户信息*/
        int deleteUser(@Param("userName") String userName);

        /*判断用户是否绑定角色*/
        List<Role> isUserRole(@Param("userName") String userName);

        /*为用户添加角色*/
        int addUserRole(UserRole userRole);

        /*删除角色绑定*/
        int deleteUserRole(UserRole userRole);

        /*根据userName删除角色绑定*/
        int deleteUserRoleByUserName(@Param("userName") String userName);

        /*用户部门*/
        List<UserDepart> getUserDepart();

        /*获取用户信息记录数*/
        int getUserNum();

        /*依据id获取用户对象*/
        User getUserById(@Param("id") Integer id);
}