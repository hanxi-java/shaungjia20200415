package shuangjia.shuangjia.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shuangjia.shuangjia.entities.DayData;
import shuangjia.shuangjia.entities.HourData;
import shuangjia.shuangjia.entities.MonthData;
import shuangjia.shuangjia.service.DayDataService;
import shuangjia.shuangjia.service.HourDataService;
import shuangjia.shuangjia.service.MonthdataService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@CrossOrigin
@RestController
public class MonthDataController {

    @Resource
    private DayDataService dayDataService;
    @Resource
    private HourDataService hourDataService;
    @Resource
    private MonthdataService monthDataService;

    //每月1日的00:30分生成上月数据，将搜集到的数据按月份分组，作为年报的基础
    @Scheduled(cron = "0 30 0 1 1/1  *")
    public void getMonthData() throws ParseException {
        //得到标准格式的当前日期,当前日期为查询的结尾日期
        //对日期进行格式化
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String endMonthFormat = df.format(new Date());
        //从tb_report_day_data表中得到上月的数据（MonthData）
        List<MonthData> monthDataList = monthDataService.getMonthData(endMonthFormat);

        //将得到的数据插入按月汇总的表（ tb_report_month_data）
        if (monthDataList.size() > 0) {
           Integer result = monthDataService.insert(monthDataList);
        }
    }


    //获取月报数据
    @PostMapping("/getMonthReport")
    public List<DayData> getMonthReportData(HttpServletRequest request) {
        DayData dayData = (DayData) request.getAttribute("dayData");
        System.out.println(dayData);
        List<DayData> dayDataList=dayDataService.queryDayDataList(dayData);

        //将得到的数据插入按天汇总的表（ tb_report_day_data）
        if (dayDataList.size() > 0) {
            System.out.println(dayDataList);
        }
        return dayDataList;
    }



}