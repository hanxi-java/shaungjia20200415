package shuangjia.shuangjia.service;

import shuangjia.shuangjia.entities.User;
import shuangjia.shuangjia.entities.zEntities.ItemType;

import java.util.List;

/**
*@Author 张典
*@Description 角色业务接口
 *
*/
public interface ItemService {
        List showItem();
        List showUserItem(User user);
        List showUserItemType(ItemType itemType);
}
