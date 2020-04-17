package shuangjia.shuangjia.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Mapper
public class DevicePropertyValueDefine {
    public int id;
    public int PropertyId;
    public int PropertyValue;
    public String propertyDefine;
}
