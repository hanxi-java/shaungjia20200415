package shuangjia.shuangjia.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import shuangjia.shuangjia.entities.AlarmData;


import java.util.Date;
import java.util.List;


@Mapper
public interface AlarmDataDao {

    //获取所有报警数据，显示到前台
    List<AlarmData> getAll(@Param("beginIndex") Integer beginIndex,
                           @Param("pageSize") Integer pageSize,
                           @Param("alarmData") AlarmData alarmData,
                           @Param("dateBegin") Date dateBegin,
                           @Param("dateEnd") Date dateEnd);

    //获取所有报警数据，后台使用
    List<AlarmData> getAllWithoutCondition();

    //获取报警的数量
    Integer getTotal(@Param("alarmData") AlarmData alarmData,
                     @Param("dateBegin") Date dateBegin,
                     @Param("dateEnd") Date dateEnd);


    //将重组得到的数据的插入数据库（tb_alarm_data）
    int insert(@Param("alarmDatas") List<AlarmData> alarmDatas);

    //批量更新
    int update(@Param("updateAlarmDataList") List<AlarmData> updateAlarmDataList);

    //全部删除
    void deleteAll();

}
