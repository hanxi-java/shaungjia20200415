package shuangjia.shuangjia.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Mapper
public class AlarmDefine {
    public int id;
    public String name;
    public int alarmTypeId;//报警类型
    public int gradeId;//等级
    public int devicePropertyId;//数据类型
    public float val;//超限程度值
    public int isStop;//是否停用
    public int triggerNum;//触发次数（在需要报警的情况下，大于该触发次数，则产生一条报警记录）
    public int DevicePropertyValueDefineId;//--故障报警值（当报警类型为故障报警即时，设定的对应值如1,2,0等，根据监控返回值故障定义）
    public int faultTypeId;//故障分类

    public int deviceid;//设备id
    public int branchid;//水厂id

    public String isStopname;//是否停用
    public String alarmTypename;
    public String gradename;
    public String devicePropertyname;
    public String faultTypename;
    public String propertyDefine;//设备属性开关量定义

    public int pagecount;

}
