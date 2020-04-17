package shuangjia.shuangjia.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import shuangjia.shuangjia.entities.DayData;
import shuangjia.shuangjia.entities.HourData;

import java.util.List;

@Mapper
public interface DayDataDao {

    //向按天汇总的报表（tb_report_day_data）插入数据
    int insert(@Param("dayDataList") List<DayData> dayDataList);

    //从tb_report_day_data表中得到日报数据，实现多条件动态查询
    List<DayData> queryDayDataList(@Param("dayData") DayData dayData);

    //从tb_report_day_data表中得到每天的数据，按月进行汇总
    List<DayData> getMonthData(@Param("beginMonthFormat") String beginMonthFormat);
}
