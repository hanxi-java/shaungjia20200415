package shuangjia.shuangjia.service;

import org.springframework.stereotype.Service;
import shuangjia.shuangjia.dao.*;
import shuangjia.shuangjia.entities.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class TbAlarmServiceImpl implements TbAlarmService {
    @Resource
    private TbAlarmDao tbAlarmDao;

    @Resource
    private UserDao userDao;

    @Resource
    private AlarmDefineDao alarmDefineDao;

    @Resource
    private DevicePropertyDao devicePropertyDao;

    @Resource
    private DataTypeDao dataTypeDao;

    @Resource
    private DeviceDao deviceDao;

    @Resource
    private AlarmDao alarmDao;

    //从tb_alarm中获取所有的报警数据并进行重组
    @Override
    public List<AlarmData> getAlarmData() {
        //定义将要tb_alarm_data的数据集合
        List<AlarmData> alarmDataList = new ArrayList<>();

        //从tb_alarm中获取所有的报警数据
        List<Tbalarm> tbalarmList = tbAlarmDao.getAll();
        if (tbalarmList.size() > 0) {
            for (Tbalarm tbalarm : tbalarmList) {
                //定义tb_alarm_data单个对象
                AlarmData alarmData = new AlarmData();

                //设置数据类型信息
                if(tbalarm.getDevicePropertyId() > 0){
                    DeviceProperty deviceProperty = devicePropertyDao.getDevicePropertyById(tbalarm.getDevicePropertyId());
                    if (deviceProperty != null){
                        //设置设备信息
                        alarmData.setDeviceId(deviceProperty.getDeviceId());
                        Device device = deviceDao.getDeviceByid(deviceProperty.getDeviceId());
                        if (device != null&& device.getId()>0) {
                            alarmData.setDeviceName(device.getName());
                            //一期还是二期
                            alarmData.setStage(device.getRemark());
                        }

                        DataType dataType = dataTypeDao.getDataTypeById(deviceProperty.getDataTypeId());
                        if (dataType != null) {
                            alarmData.setDataTypeId(dataType.getId());
                            alarmData.setDataTypeName(dataType.getDataTypeName());
                        }
                        //设置单位
                        alarmData.setDataUnit(deviceProperty.getDataUnit());
                    }
                }

                //设置报警数据类型信息
                AlarmDefine alarmDefine = alarmDefineDao.getAlarmDefineById(tbalarm.getAlarmDefineId());
                if (alarmDefine != null) {
                    alarmData.setAlarmType(alarmDefine.getName());
                    grade grade1 = alarmDao.getGradeById(alarmDefine.getGradeId());
                    if (grade1 != null) {
                        //设置报警等级
                        alarmData.setGrade(grade1.getName());
                    }
                }

                //设置当前值
                alarmData.setMonitorValue(tbalarm.getAlarmValue());


                //得到报警时间
                alarmData.setAlarmTime(tbalarm.getAlarmTime());

                //设置报警是否被处理
                String alarmState = "未处理";
                if (tbalarm.getAlarmStateId() == 2) {
                    alarmState = "已处理";
                }
                alarmData.setDealState(alarmState);

                //设置处理内容
                alarmData.setDealContent(tbalarm.getDealContent());

                //设置处理时间
                alarmData.setDealTime(tbalarm.getDealTime());

                //设置处理人id
                alarmData.setDealPersonId(tbalarm.getDealPersonId());

                //设置处理人的姓名
                User user = userDao.getUserById(tbalarm.getDealPersonId());
                if (user != null) {
                    alarmData.setDealPerson(user.getName());
                }
                if (alarmData.getDeviceName() != null && alarmData.getAlarmType() != null &&
                        alarmData.getGrade() != null) {
                    alarmDataList.add(alarmData);
                }

                }
        }
        return alarmDataList;
    }
}
