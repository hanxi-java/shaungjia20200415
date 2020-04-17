package shuangjia.shuangjia.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Mapper
public class AlarmDefineParameter {
    public List<FaultType> faultTypes;//故障分类
    public List<AlarmType> alarmTypes;//报警类型
    public List<grade> grades;//等级
    public List<DevicePropertyValueDefine> devicePropertyValueDefine;//设备属性开关量定义
   // public List<DeviceProperty> devicePropertys;//设备数据类型
    public String devicePropertytree;
}
