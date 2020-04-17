package shuangjia.shuangjia.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import shuangjia.shuangjia.entities.Item;
import shuangjia.shuangjia.entities.Module;
import shuangjia.shuangjia.entities.zEntities.ItemType;

import java.util.List;

/**
*@Author 张典
*@Description 菜单数据访问
 *
*/
@Mapper
public interface ItemDao {
    /*一级菜单*/
    List<Module> showModule();
    /*二级普通菜单*/
    List<Item> showItem(@Param("groupId") int groupId);
    /*二级按钮*/
    List<ItemType> showItemType(@Param("groupId") int groupId,
                                @Param("parentId") int parentId);

    /*一级菜单*/
    List<Module> userModule(@Param("userName") String userName);
    /*二级普通菜单*/
    List<Item> userItem(@Param("userName") String userName,
                        @Param("groupId") int groupId);
    /*二级按钮*/
    List<ItemType> userItemType(@Param("userName") String userName,
                                @Param("parentId") int parentId);
}
