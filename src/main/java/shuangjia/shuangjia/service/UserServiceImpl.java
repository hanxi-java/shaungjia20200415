package shuangjia.shuangjia.service;

import org.springframework.stereotype.Service;
import shuangjia.shuangjia.dao.UserDao;
import shuangjia.shuangjia.entities.*;

import javax.annotation.Resource;
import java.util.List;

/**
*@Author 张典
*@Description 用户信息业务处理
 *
*/
@Service
public class UserServiceImpl implements  UserService{
    @Resource
    private UserDao userDao;
    @Override
    public User loginRequired(String userName,String pwd) {
        Boolean flag=false;
        User user=userDao.loginRequired(userName,pwd);
        return user;
    }

    @Override
    public User getUserInfoByUserName(String userName) {
        return userDao.getUserInfoByUserName(userName);
    }

    @Override
    public List<User> getAllUserInfo(int index,int pageSize) {
        return userDao.getAllUserInfo(index,pageSize);
    }

    @Override
    public int updateUserInfo(User user) {

        return userDao.updateUserInfo(user);
    }

    @Override
    public int addUser(User user) {
        return userDao.addUser(user);
    }

    @Override
    public int deleteUser(String userName,int choose) {
        int result=0;
        if(choose==1){
        /*先删除用户绑定角色*/
        int result1=userDao.deleteUserRoleByUserName(userName);
         result=userDao.deleteUser(userName);
        }else if (choose==2){
            /*只删除该用户绑定角色*/
             result=userDao.deleteUserRoleByUserName(userName);
        }
        return result;
    }

    @Override
    public int addUserRole(UserRole userRole) {
        return userDao.addUserRole(userRole);
    }

    @Override
    public List<Role> isUserRole(String userName) {
        return userDao.isUserRole(userName);
    }

    @Override
    public int deleteUserRole(UserRole userRole) {
        return userDao.deleteUserRole(userRole);
    }

    @Override
    public List<UserDepart> getUserDepart() {
        return userDao.getUserDepart();
    }
}
