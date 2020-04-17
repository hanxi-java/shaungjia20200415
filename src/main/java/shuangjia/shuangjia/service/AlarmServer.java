package shuangjia.shuangjia.service;

import org.apache.ibatis.annotations.Param;
import shuangjia.shuangjia.entities.*;

import java.text.ParseException;
import java.util.List;

public interface AlarmServer {
    String gettree();
    List<DeviceProperty> GetDevicePropertybydeviceid(@Param("deviceid") int deviceid);//查询设备数据类型
    int Updatealarmvalue(Alarmdevicemodel mo);
    int UpdateFaultType(FaultType fa);
    int AddFaultType(FaultType fa);
    int DelteFaultType(int id);//删除故障分类
    List<FaultType> getFaultType();
    List<AlarmType> getAlarmType();
    List<grade> getgrade();
    int Updategrade(grade mo);//设置等级
    int Addgrade(grade fa);//添加等级
    int Deltegrade(@Param("id") int  id);//删除等级
    AlarmDefines getAlarmDefine(@Param("pageIndex")int index,@Param("pageIndex") int pageSize,@Param("wheres") String wheres);//查询故障定义
    AlarmDefines getAlarmDefineByWhere(@Param("pageIndex")int index,@Param("pageIndex") int pageSize,@Param("wheres") String wheres);//根据条件查询故障定义
    int UpdAlarmDefine(AlarmDefine mo);//修改故障定义
    int AddAlarmDefine(AlarmDefine fa);//添加故障定义
    int DeleteAlarmDefine(@Param("id") int  id);//删除故障定义
    List<DevicePropertyValueDefine> getDevicePropertyValueDefine();//查询设备属性开关量定义
    List<DevicePropertyValueDefine> getDevicePropertyValueDefinebydeviceid(@Param("deviceid") int deviceid);//查询设备属性开关量定义
    int UpdDevicePropertyValueDefine(DevicePropertyValueDefine mo);//修改设备属性开关量定义
    int AddDevicePropertyValueDefine(DevicePropertyValueDefine fa);//添加设备属性开关量定义
    int DeleteDevicePropertyValueDefine(@Param("id") int  id);//删除设备属性开关量定义
    List<alarmstate> getalarmstate();//获取报警状态
    tbalarms gettbalarm(@Param("pageIndex")int index,@Param("pageIndex") int pageSize,@Param("wheres") String wheres);//获取报警记录
    int Uptbalarm(Tbalarm mo);//修改报警记录//处理报警
    tbalarms gethistorytbalarm(@Param("pageIndexs") int pageIndexs, @Param("pageSize") int pageSize);//获取历史报警记录
    AlarmDefineParameter GetAlarmDefineparameter();//获取报警定义界面参数
    /*张典*/
    List<AlarmDefine> getAllAlarmDefine();
    void autoAlarm(AlarmDefine alarmDefine,String alarmTime,String value) throws ParseException;
}
