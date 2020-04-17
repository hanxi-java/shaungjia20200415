package shuangjia.shuangjia.service;

import shuangjia.shuangjia.entities.AlarmData;
import shuangjia.shuangjia.entities.Paging;

import java.text.ParseException;
import java.util.List;

public interface AlarmDataService {
    //按条件获取报警数据，显示到前台
    List<AlarmData> getAll(AlarmData alarmData) throws ParseException;

    //获取所有报警数据，显示到前台
    List<AlarmData> getAllWithoutCondition();

    //获取报警的数量
    Integer getTotal(AlarmData alarmData) throws ParseException;

    //将重组得到的数据的插入数据库（tb_alarm_data）
    int insert(List<AlarmData> alarmDatas);


}
