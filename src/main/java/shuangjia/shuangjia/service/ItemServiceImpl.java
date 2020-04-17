package shuangjia.shuangjia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shuangjia.shuangjia.dao.ItemDao;
import shuangjia.shuangjia.entities.*;
import shuangjia.shuangjia.entities.zEntities.ItemType;

import java.util.List;

/**
*@Author 张典
*@Description 角色业务逻辑层
 *
*/
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    private ItemDao itemDao;

    @Override
    public List showItem() {
        /*一级菜单*/
        List<Module> modules=itemDao.showModule();
        /*二级普通菜单*/
        for (Module module:modules){
            List<Item> items=itemDao.showItem(module.getModuleId());
                for (Item item:items){
                    /*按钮菜单*/
                    List<ItemType> itemTypes=itemDao.showItemType(module.getModuleId(),item.getItemId());
                    item.setChildren(itemTypes);
                }
            module.setChildren(items);
        }
        return modules;
    }

    @Override
    public List showUserItem(User user) {
        /*一级菜单*/
        List<Module> modules=itemDao.userModule(user.getUserName());
        /*二级普通菜单*/
        for (Module module:modules){
            List<Item> items=itemDao.userItem(user.getUserName(),module.getModuleId());
            module.setChildren(items);
        }
        return modules;
    }

    @Override/*按钮_用户*/
    public List showUserItemType(ItemType itemType) {
        List<ItemType> items=itemDao.userItemType(itemType.getUserName(),itemType.getId());
        return items;
    }
}
