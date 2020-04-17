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
public class Tbalarm extends Paging {
    public int id;
    public int alarmDefineId;//报警方案Id
    public int devicePropertyId;//设备属性Id
    public String alarmContent;//报警内容
    public String alarmTime;//报警时间
    public int alarmStateId;//报警状态
    public String dealContent;//处理内容;
    public String dealTime;//处理时间
    public int dealPersonId;//处理人
    private String alarmValue;//监控值

    public String alarmDefinename;
    public String devicePropertyname;
    public String alarmState;
    public String dealPerson;
    public String gradename;//等级
}
