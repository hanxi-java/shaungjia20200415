package shuangjia.shuangjia.service;

import org.apache.ibatis.annotations.Param;
import shuangjia.shuangjia.entities.HourData;

import java.util.List;

public interface HourDataService {

    /**
    * 向tb_report_hour_data表中插入hourDataList数据，hourDataList是按数据类型、设备、
    * 小时汇总生成的数据
    * */
    int insert( List<HourData> hourDataList);

    //得到日报数据
    List<HourData> getDayReportData(String stage,Integer deviceId,
                                    String reportDate,Integer dataTypeId);


    /**
     * 获取日报数据，日报的数据实际上是tb_report_hour_data表中的hourDataList，
     * 传入对象参数可实现多条件动态查询
     * */
    List<HourData> queryHourDataList( HourData reportHourData );
//     后续可能分页                      @Param("rowIndex") Integer rowIndex,
//                                     @Param("pageSize") Integer pageSize);
}
