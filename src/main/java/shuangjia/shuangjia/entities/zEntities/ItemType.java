package shuangjia.shuangjia.entities.zEntities;

import lombok.Data;

/**
*@Author 张典
*@Description 菜单类型分类
 *
*/
@Data
public class ItemType {
    private int id;

    private int groupId;

    private String code;

    private String label;

    private String description;

    private int visible;

    private int itemOrder;

    private String itemTypeId;

    private int parentId;
    /*用户名*/
    private String userName;
}
