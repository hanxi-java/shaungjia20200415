package shuangjia.shuangjia.service;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shuangjia.shuangjia.entities.Role;
import shuangjia.shuangjia.entities.User;
import shuangjia.shuangjia.entities.UserDepart;
import shuangjia.shuangjia.entities.UserRole;

import java.util.List;

/**
*@Author 张典
*@Description 用户服务service接口
 *
*/

public interface UserService {
        User loginRequired(String userName,String psw);
        User  getUserInfoByUserName(String userName);
        List<User> getAllUserInfo(int index,int pageSize);
        int  updateUserInfo(User user);
        int addUser(User user);
        int deleteUser(String userName,int choose);
        int addUserRole(UserRole userRole);
        List<Role> isUserRole(String userName);
        int deleteUserRole(UserRole userRole);
        List<UserDepart> getUserDepart();
}
