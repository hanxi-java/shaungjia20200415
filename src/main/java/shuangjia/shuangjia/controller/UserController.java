package shuangjia.shuangjia.controller;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.internal.cglib.asm.$Attribute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import shuangjia.shuangjia.entities.*;
import shuangjia.shuangjia.service.ItemService;
import shuangjia.shuangjia.service.RoleService;
import shuangjia.shuangjia.service.TokenService;
import shuangjia.shuangjia.service.UserService;
import shuangjia.shuangjia.util.EncryptUtil;
import shuangjia.shuangjia.util.Page;

import java.util.*;

/**
*@Author 张典
*@Description 用户信息Controller
 *
*/
@CrossOrigin
@RestController
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private ItemService itemService;
    /*用户登录*/
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public CommonResult login(@RequestBody User user1) {

        Map map = new HashMap();
        Boolean flag = null;
        String md5= EncryptUtil.getInstance().MD5(user1.getPsw());//MD5加密
        System.out.println("md5:"+md5);
        User user =userService.loginRequired(user1.getUserName(),md5);
        List list=itemService.showUserItem(user);
        if(!(user==null)){
            if(user1.getUserName().equals(user.getUserName())){
                flag=true;
            }else {
                flag=false;
            }
        }else {
            flag=false;
        }
        // 登录成功添加token验证
        if (flag==true) {
            log.info("*********用户登陆成功*********;用户名："+user1.getUserName());
            user.setUserName(user1.getUserName());
            user.setPsw(user1.getPsw());
            String token=tokenService.getToken(user);
            map.put("id",user.getId());
            map.put("userName",user.getUserName());
            map.put("name",user.getName());
            map.put("roleType",user.getRole().getRoleType());
            map.put("roleName",user.getRole().getRoleName());
            map.put("token",token);
            map.put("item",list);
            return new CommonResult("200", "登陆成功", map);
        }else{
            log.error("******用户登陆失败*******");
            return new CommonResult("400", "登陆失败,用户不存在",null);
        }
    }
    /*用户信息列表*/
    @RequestMapping(value="/user/allInfo",method = RequestMethod.POST)
    public CommonResult getAllUserInfo(@RequestBody UserRole userRole){
        int size= Page.calculateRowIndex(Integer.valueOf(userRole.getPaging().getIndex()),Integer.valueOf(userRole.getPaging().getPageSize()));
        List list=new ArrayList();
        List<UserDepart> userDeparts=userService.getUserDepart();
        int number =0;
        if(userRole.getRole().getRoleType()==1) {
            List<User> userlist = userService.getAllUserInfo(size,Integer.valueOf(userRole.getPaging().getPageSize()));
            List<User> userNumber = userService.getAllUserInfo(0,1000);
            number=userNumber.size();
            for (User u : userlist) {
                Map map = new HashMap();
                map.put("id",u.getId());
                map.put("userName", u.getUserName());
                map.put("name", u.getName());
                map.put("tele", u.getTele());
                map.put("email", u.getEmail());
                for(UserDepart userDepart:userDeparts){
                    if(userDepart.getId()==u.getAdDeptId()){
                        map.put("adDeptId",userDepart.getName());
                    }else {
                    }
                }
                map.put("weixinId", u.getWeixinId());
                map.put("remark", u.getRemark());
                map.put("state", u.getState());
                List<Role> roles=userService.isUserRole(u.getUserName());
                map.put("role",roles);
                list.add(map);
            }
        }else {
            number=1;
            User user=userService.getUserInfoByUserName(userRole.getUser().getUserName());
            if (user!=null) {
                Map map = new HashMap();
                map.put("id",user.getId());
                map.put("userName", user.getUserName());
                map.put("name", user.getName());
                map.put("tele", user.getTele());
                map.put("email", user.getEmail());
                for(UserDepart userDepart:userDeparts){
                    if(userDepart.getId()==user.getAdDeptId()){
                        map.put("adDeptId",userDepart.getName());
                    }else {
                    }
                }
                map.put("weixinId", user.getWeixinId());
                map.put("remark", user.getRemark());
                map.put("state", user.getState());
                List<Role> roles=userService.isUserRole(user.getUserName());
                map.put("role",roles);
                list.add(map);
            }else {
                list=null;
            }

        }
        if(list !=null){
            log.info("用户列表查询成功");
            return new CommonResult("200", String.valueOf(number), list);
        }else {
            return new CommonResult("400", "用户列表查询失败", null);
        }
    }
    /*修改密码*/
    @RequestMapping(value = "/user/updatePwd",method = RequestMethod.POST)
    public CommonResult updatePwd(@RequestBody User myUser){
        String md5=EncryptUtil.getInstance().MD5(myUser.getPsw());
        User user =userService.getUserInfoByUserName(myUser.getUserName());
        if(user!=null){
            user.setPsw(md5);
            int result=userService.updateUserInfo(user);
            if(result>0) {
                return new CommonResult("200", "密码修改成功", null);
            }else {
                return new CommonResult("400", "密码修改失败", null);
            }
        }else {
            return new CommonResult("401", "用户不存在", null);
        }
    }
    /*新增用户*/
    @RequestMapping(value = "/user/addUser",method = RequestMethod.POST)
    public CommonResult addUser(@RequestBody UserRole userRole){
        /*判断用户登录名是否重复*/
        User userByUserName=userService.getUserInfoByUserName(userRole.getUser().getUserName());
        if (userByUserName!=null) {
            return new CommonResult("401    ","登录名重复",null);
        }
        /*密码加密*/
        String md5=EncryptUtil.getInstance().MD5(userRole.getUser().getPsw());
        userRole.getUser().setPsw(md5);
        userRole.getUser().setState("正常");
        int result=userService.addUser(userRole.getUser());
        /*为用户添加角色*/
        if(userRole.getRoles()!=null) {
        List<Role> roleList= Arrays.asList(userRole.getRoles());
            User newUser = userService.getUserInfoByUserName(userRole.getUser().getUserName());
            userRole.setUserId(newUser.getId());
            for (Role role : roleList) {
                userRole.setRoleId(role.getRid());
                int result1 = userService.addUserRole(userRole);
            }
        }
        if(result>0){
            return new CommonResult("200", "用户添加成功", null);
        }else {
            return new CommonResult("400", "用户添加失败", null);
        }
    }

    /*删除用户及绑定角色*/
    @RequestMapping(value = "/user/deleteUser",method = RequestMethod.POST)
    public CommonResult deleteUser(@RequestBody User user){
        int result=userService.deleteUser(user.getUserName(),1);
        if(result>0){
            return new CommonResult("200", "用户删除成功", null);
        }else {
            return new CommonResult("400", "无删除操作", null);
        }
    }
    /*修改用户信息*/
    @RequestMapping(value = "/user/updateUser",method = RequestMethod.POST)
    public CommonResult updateUser(@RequestBody UserRole userRole){
        /*修改用户*/
        int result=userService.updateUserInfo(userRole.getUser());
        /*删除该用户绑定角色*/
        if(result>0 ){
            if(userRole.getRoles().length>0) {
                int roleReault = 0;
                int choose = userService.deleteUser(userRole.getUser().getUserName(), 2);
                /*为用户添加角色*/
                List<Role> roleList = Arrays.asList(userRole.getRoles());
                if (roleList.size() > 0) {
                    User newUser = userService.getUserInfoByUserName(userRole.getUser().getUserName());
                    userRole.setUserId(newUser.getId());
                    for (Role role : roleList) {
                        userRole.setRoleId(role.getRid());
                        roleReault += userService.addUserRole(userRole);
                    }
                }
                return new CommonResult("200", "用户绑定角色信息修改成功", roleReault);
            }
            return new CommonResult("200", "用户信息修改成功", result);
        }else {
            return new CommonResult("400", "用户信息修改失败", result);
        }
    }
    /*展示用户绑定角色信息*/
    @RequestMapping(value = "/user/showUserRole",method = RequestMethod.POST)
    public CommonResult showUserRole(@RequestBody User user){
        List<Role> roleList=userService.isUserRole(user.getUserName());
        if (roleList.size()>0){
            return new CommonResult("200",user.getUserName(),roleList);
        }else {
            return new CommonResult("400","该用户没有绑定角色",null);
        }
    }
    /*获取用户部门*/
    @GetMapping(value = "/user/getUserDepart")
    public CommonResult getUserDepart(){
        List<UserDepart> userDeparts=userService.getUserDepart();
        List<Role> roleList=roleService.showRole();
        List list=new ArrayList();
        list.add(userDeparts);
        list.add(roleList);
        if (userDeparts.size()>0){
            return new CommonResult("200","用户部门查询成功",list);
        }else {
            return new CommonResult("400","用户部门查询失败",null);
        }
    }
}
