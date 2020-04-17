package shuangjia.shuangjia.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import shuangjia.shuangjia.entities.MonthData;

import java.util.List;

@Mapper
public interface MonthDataDao {

    int insert(@Param("monthDataList") List<MonthData> monthDataList);

    //获取年报数据
    List<MonthData> queryMonthDataList(@Param("monthData") MonthData monthData);
}
