package shuangjia.shuangjia.service;

import shuangjia.shuangjia.entities.DayData;
import shuangjia.shuangjia.entities.MonthData;

import java.text.ParseException;
import java.util.List;

public interface MonthdataService {

    //从日期表（tb_report_day_data）中按月份获取该月每天的数据
    List<MonthData> getMonthData(String endDateFormat) throws ParseException;

    //插入按月生成的数据
    int insert(List<MonthData> monthDataList);

    //获取年报数据
    List<MonthData> queryMonthDataList(MonthData monthData);
}
