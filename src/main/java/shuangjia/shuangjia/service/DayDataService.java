package shuangjia.shuangjia.service;

import shuangjia.shuangjia.entities.DayData;

import java.text.ParseException;
import java.util.List;

public interface DayDataService {

    //从tb_report_hour_data表中得到每天的数据（DayData）
    List<DayData> getDayData(String endDateFormat) throws ParseException;

    // 将每天生成的数据按照不同的设备、类型汇总，插入到每天汇总表（tb_report_day_data）中。
    int insert(List<DayData> monthDataList);

    //获取月报数据
    List<DayData> queryDayDataList(DayData dayData);
}
