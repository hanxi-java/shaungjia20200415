package shuangjia.shuangjia.service;

import shuangjia.shuangjia.entities.AlarmData;

import java.util.List;

public interface TbAlarmService {

    //获取重组后的AlarmData集合
    List<AlarmData> getAlarmData();
}
