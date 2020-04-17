package shuangjia.shuangjia.dao;

import org.apache.ibatis.annotations.Mapper;
import shuangjia.shuangjia.entities.Tbalarm;

import java.util.List;

@Mapper
public interface TbAlarmDao {

    //从tb_alarm中获取所有的报警数据
    List<Tbalarm> getAll();

}
