package shuangjia.shuangjia.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import shuangjia.shuangjia.entities.HourData;

import java.util.List;

@Mapper
public interface HourDataDao {
    /**
     * 向tb_report_hour_data表中插入hourDataList数据，hourDataList是按数据类型、设备、
     * 小时汇总生成的数据
     * */
    int insert(@Param("hourDataList") List<HourData> hourDataList);

       //得到日报数据
    List<HourData> getDayReportData(@Param("stage") String stage,
                                    @Param("deviceId") Integer deviceId,
                                    @Param("reportDate") String reportDate,
                                    @Param("dataTypeId") Integer dataTypeId);

    //从tb_report_hour_data表中得到每天的数据，按照每天的起止时间进行截取,得到 List<HourData>
    List<HourData>  getDayData(@Param("beginDateFormat") String beginDateFormat);

    //从tb_report_hour_data表中得到日报数据，实现多条件动态查询
    List<HourData> queryHourDataList(@Param("reportHourData") HourData reportHourData );
//     后续可能分页                      @Param("rowIndex") Integer rowIndex,
//                                     @Param("pageSize") Integer pageSize);

}
