package shuangjia.shuangjia.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
/**
*@Author
*@Description  设备对应属性类 简
 *
*/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Mapper
public class Devicetypedatamodel {
    private  int id;

    public String name;

    public String value;
    //设备ID;
    public int deviceId;
    //描述
    public String shortName;
    //单位
    public String typeUnit;
    //时间
    public String monitorDate;

    public String datatypeName;
    //值
    private String monitorValue;

    private String property_define;

    public String getDatatypeName() {
        return datatypeName;
    }

    public void setDatatypeName(String datatypeName) {
        this.datatypeName = datatypeName;
    }

}
