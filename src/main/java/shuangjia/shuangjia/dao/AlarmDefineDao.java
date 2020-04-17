package shuangjia.shuangjia.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import shuangjia.shuangjia.entities.AlarmDefine;

@Mapper
public interface AlarmDefineDao {

    //按照id查询alarmDefine对象
    AlarmDefine getAlarmDefineById(@Param("id") Integer id);

}
