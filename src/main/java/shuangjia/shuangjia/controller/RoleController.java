package shuangjia.shuangjia.controller;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import shuangjia.shuangjia.entities.*;
import shuangjia.shuangjia.service.RoleService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *@Author 张典
 *@Description 角色信息Controller
 *
 */
@CrossOrigin
@RestController
@Slf4j
public class RoleController {
    @Autowired
    private RoleService roleService;

    /*角色列表*/
    @GetMapping(value = "/role/showRole")
    public CommonResult showRole(){
        List<Role> roleList=roleService.showRole();
        if (roleList.size()>0){
            log.info("用户信息列表查询成功");
            return new CommonResult("200","角色信息列表",roleList);
        }else {
            return new CommonResult("400","角色列表查询失败",null);
        }
    }
    /*新增角色*/
    @RequestMapping(value = "/role/addRole",method = RequestMethod.POST)
    public CommonResult addRole(@RequestBody  UserRoleDuty userRoleDuty){
        /*新增角色*/
        int result=roleService.addRole(userRoleDuty.getRole());
        /*添加菜单权限*/
        List<Item> items=Arrays.asList(userRoleDuty.getItems());
        if(result>0){
            int resultDuty=0;
            Role role=roleService.getRoleByType(userRoleDuty.getRole().getRoleType());
            userRoleDuty.setRoleId(role.getRid());
            for (Item item:items){
            userRoleDuty.setItemId(item.getItemId());
             resultDuty+=roleService.addRoleDuty(userRoleDuty);
            }
            return new CommonResult("200","角色添加成功",resultDuty);
        }else {
            return new CommonResult("400","角色添加失败",result);
        }
    }
    /*删除角色及绑定菜单项*/
    @RequestMapping(value = "/role/deleteRole",method = RequestMethod.POST)
    public CommonResult deleteRole(@RequestBody Role role){
        int result=roleService.deleteUserRole(role.getRid(),1);
        if(result>0){
            return new CommonResult("200","角色删除成功",result);
        }else {
            return new CommonResult("400","删除操作未进行",result);
        }
    }
    /*修改角色信息*/
    @RequestMapping(value = "/role/updateRole",method = RequestMethod.POST)
    public CommonResult updateRole(@RequestBody UserRoleDuty userRoleDuty){
        int result=roleService.updateRole(userRoleDuty.getRole());
        if (result>0) {
            /*添加菜单权限*/
            if (userRoleDuty.getItems().length > 0) {
            List<Item> items = Arrays.asList(userRoleDuty.getItems());
                /*删除旧的菜单权限*/
                int choose = roleService.deleteUserRole(userRoleDuty.getRole().getRid(), 2);
                int resultDuty = 0;
                userRoleDuty.setRoleId(userRoleDuty.getRole().getRid());
                for (Item item : items) {
                    userRoleDuty.setItemId(item.getItemId());
                    resultDuty += roleService.addRoleDuty(userRoleDuty);
                }
                return new CommonResult("200", "角色权限修改成功", resultDuty);
            }
            return new CommonResult("200", "角色修改成功",result );
        } else {
            return new CommonResult("400","角色重复",result);
        }
    }
    /*为角色修改菜单绑定   先删除该角色下所有菜单绑定，再添加*/
    @RequestMapping(value = "/role/addRoleDuty",method = RequestMethod.POST)
    public CommonResult addRoleDuty(@RequestBody UserRoleDuty[] userRoleDutys) {
        int result=userRoleDutys.length;
        int res=0;
        /*先删除绑定*/
        List<UserRoleDuty> roleArray= Arrays.asList(userRoleDutys);
        int rid=roleArray.get(0).getRoleId();
        int deleteRes=roleService.deleteUserRole(rid,2);
        /*再添加*/
        for(UserRoleDuty userRoleDuty:userRoleDutys){
            res+=roleService.addRoleDuty(userRoleDuty);
        };
        if(result==res){
            return new CommonResult("200","修改成功",result);
        }else {
            return new CommonResult("400","修改出错",res);
        }
    }
    /*展示角色绑定菜单*/
    @RequestMapping(value = "/role/showRoleDuty",method = RequestMethod.POST)
    public CommonResult showRoleDuty(@RequestBody UserRoleDuty userRoleDuty){

        List list=roleService.showRoleDuty(userRoleDuty);

        return new CommonResult("200",String.valueOf(userRoleDuty.getRoleId()),list);
    }
}
