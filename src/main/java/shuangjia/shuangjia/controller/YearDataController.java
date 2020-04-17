package shuangjia.shuangjia.controller;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import shuangjia.shuangjia.entities.DayData;
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
public class YearDataController {
    @Resource
    private MonthdataService monthdataService;

    //获取年报数据
    @PostMapping("/getYearReport")
    public List<MonthData> getMonthReportData(HttpServletRequest request) {
        MonthData monthData = (MonthData) request.getAttribute("monthData");
        System.out.println(monthData);
        List<MonthData> monthDataList=monthdataService.queryMonthDataList(monthData);
        return monthDataList;
    }
}