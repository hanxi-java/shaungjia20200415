package shuangjia.shuangjia.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import shuangjia.shuangjia.entities.CommonResult;
import shuangjia.shuangjia.entities.User;
import shuangjia.shuangjia.entities.zEntities.ItemType;
import shuangjia.shuangjia.service.ItemService;

import java.util.List;

/**
 *@Author 张典
 *@Description 菜单信息Controller
 *
 */
@CrossOrigin
@RestController
@Slf4j
public class ItemController {
    @Autowired
    private ItemService itemService;

    /*展示菜单*/
    @GetMapping(value = "item/showItem")
    public CommonResult showItem(){

        List list=itemService.showItem();
        if (list!=null) {
            return new CommonResult("200", "菜单树状结构查询成功", list);
        }else {
            return new CommonResult("400", "菜单树状结构查询失败", null);
        }
    }

    /*展示用户菜单 菜单栏树状图二级*/
    @RequestMapping(value = "item/showUserItem",method = RequestMethod.POST)
    public CommonResult showUserItem(@RequestBody User user){

        List list=itemService.showUserItem(user);

        if (list!=null) {
            return new CommonResult("200", user.getUserName(), list);
        }else {
            return new CommonResult("400", "菜单树状结构查询失败", null);
        }
    }
    /*展示用户按钮*/
    @RequestMapping(value = "item/showUserItemType",method = RequestMethod.POST)
    public CommonResult showUserItemType(@RequestBody ItemType itemType){

        List list=itemService.showUserItemType(itemType);

        if (list!=null) {
            return new CommonResult("200", itemType.getUserName(), list);
        }else {
            return new CommonResult("400", "菜单树状结构查询失败", null);
        }
    }
}
