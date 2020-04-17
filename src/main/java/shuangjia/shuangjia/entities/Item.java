package shuangjia.shuangjia.entities;

import lombok.Data;
import shuangjia.shuangjia.entities.zEntities.ItemType;

import java.util.List;

/**
*@Author 张典
*@Description 菜单实体
 *
*/
@Data
public class Item {
    private int itemId;

    private int groupId;

    private String code;

    private String label;

    private String description;

    private int visible;

    private int itemOrder;

    private String itemTypeId;

    private String remaek;

    private int parentId;

    private List<ItemType> children;
}
