package shuangjia.shuangjia.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import shuangjia.shuangjia.entities.*;

import java.util.List;

@Mapper
public interface AlarmDao {
    int Updatealarmvalue(Alarmdevicemodel mo);//设置报警参数
    List<Aranch> GetAranch();//查询分厂
    List<Device> GetDevice();//查询设备
    List<DeviceProperty> GetDeviceProperty();//查询设备数据类型
    List<DeviceProperty> GetDevicePropertybydeviceid(@Param("deviceid") int deviceid);//查询设备数据类型
    List<Depart> GetDepart();//查询部门
    List<FaultType> getFaultType();//查询故障分类
    List<AlarmType> getAlarmType();//查询报警类型
    List<grade> getgrade();//查询等级
    grade getGradeById(@Param("id") Integer id);
    int UpdateFaultType(FaultType mo);//设置故障分类
    int AddFaultType(FaultType fa);//添加故障分类
    int DelteFaultType(@Param("id") int  id);//删除故障分类
    int Updategrade(grade mo);//设置等级
    int Addgrade(grade fa);//添加等级
    int Deltegrade(@Param("id") int  id);//删除等级
    List<AlarmDefine> getAlarmDefine(@Param("pageIndexs") int pageIndexs, @Param("pageSize") int pageSize,@Param("wheres") String wheres);//查询故障定义
    int getAlarmDefinecount(@Param("wheres") String wheres);//查询故障定义总数
    int UpdAlarmDefine(AlarmDefine mo);//修改故障定义
    int AddAlarmDefine(AlarmDefine fa);//添加故障定义
    int DeleteAlarmDefine(@Param("id") int  id);//删除故障定义
    List<DevicePropertyValueDefine> getDevicePropertyValueDefine();//查询设备属性开关量定义
    List<DevicePropertyValueDefine> getDevicePropertyValueDefinebydeviceid(@Param("deviceid") int deviceid);//查询设备属性开关量定义
    int UpdDevicePropertyValueDefine(DevicePropertyValueDefine mo);//修改设备属性开关量定义
    int AddDevicePropertyValueDefine(DevicePropertyValueDefine fa);//添加设备属性开关量定义
    int DeleteDevicePropertyValueDefine(@Param("id") int  id);//删除设备属性开关量定义
    List<alarmstate> getalarmstate();//获取报警状态
    List<Tbalarm> gettbalarm(@Param("pageIndexs") int pageIndexs, @Param("pageSize") int pageSize,@Param("wheres") String wheres);//获取报警记录
    List<User> getuser();
    int gettbalarmcount(@Param("wheres") String wheres);//获取报警记录总条数
    int Uptbalarm(Tbalarm mo);//修改报警记录//处理报警
    List<Tbalarm> gethistorytbalarm(@Param("pageIndexs") int pageIndexs, @Param("pageSize") int pageSize);//获取历史报警记录
    int gethistorytbalarmcount();//获取报警记录总条数
    List<AlarmDefine> getAlarmDefineByWhere(@Param("pageIndexs") int pageIndexs, @Param("pageSize") int pageSize,@Param("wheres") String wheres);//根据条件查询故障定义
    int getAlarmDefineByWherecount(@Param("wheres") String wheres);//根据条件查询故障定义总数
    List<AlarmDefine> gettreeid();
    /*通过设备属性ID查找报警方案 张典*/
    List<AlarmDefine> getAllAlarmDefine();
    /*插入报警记录*/
    int insertAlarm(Tbalarm alarm);
    /*查询报警处理记录*/
    List<Tbalarm> getAlarmByStateId(@Param("alarmDefineId") int alarmDefineId, @Param("devicePropertyId") int devicePropertyId);
    /*处理报警记录*/
    int updateAlarmState(Tbalarm tbalarm);
    /*判断上次报警时间*/
    Tbalarm getAlarmByStateIdTime(@Param("alarmDefineId") int alarmDefineId, @Param("devicePropertyId") int devicePropertyId);
}
