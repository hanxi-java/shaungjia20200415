package shuangjia.shuangjia.entities;

import lombok.Data;

import java.util.List;

/**
*@Author 张典
*@Description 一级菜单类
 *
*/
@Data
public class Module {
    private int moduleId;

    private String code;

    private String label;

    private int visible;

    private int moduleOrder;

    private String remark;

    /*下属二级菜单*/
    private List<Item> children;
}
