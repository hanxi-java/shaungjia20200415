package shuangjia.shuangjia.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import shuangjia.shuangjia.entities.*;
import shuangjia.shuangjia.service.DeviceService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
@CrossOrigin
@Controller
@Slf4j
public class ReportController {
    @Resource
    private DeviceService deviceService;

    @GetMapping("/toDailyReport/{stage}")
    @ResponseBody
    public List<Device> dailyReport(@PathVariable String stage) {
        //根据一期或二期向页面返回设备列表
        return deviceService.getByStage(stage);
    }

    //确定日报、月报、年报路由
    @PostMapping("/toReport")
    public String toReport(@RequestBody ReportCondition reportCondition,
                           HttpServletRequest request) {
        HourData hourData = new HourData();
        DayData dayData = new DayData();
        MonthData monthData = new MonthData();
        if (reportCondition != null && reportCondition.getReportType().equals("date")) {
                String stage = judge(reportCondition.getStage());
                hourData.setStage(stage);
                hourData.setDeviceId(reportCondition.getDeviceId());
                hourData.setReportHour(reportCondition.getReportHour());
                request.setAttribute("hourData", hourData);
                return "forward:/getDailyReport";
        } else if (reportCondition != null && reportCondition.getReportType().equals("month")) {
            String stage = judge(reportCondition.getStage());
            dayData.setStage(stage);
            dayData.setDeviceId(reportCondition.getDeviceId());
            dayData.setTimePoint(reportCondition.getReportHour());
            request.setAttribute("dayData", dayData);
            return "forward:/getMonthReport";
        } else if (reportCondition != null && reportCondition.getReportType().equals("year")){
            String stage = judge(reportCondition.getStage());
            monthData.setStage(stage);
            monthData.setDeviceId(reportCondition.getDeviceId());
            monthData.setTimePoint(reportCondition.getReportHour());
            request.setAttribute("monthData", monthData);
            return "forward:/getYearReport";
        }else{
            return null;
        }
    }
        //判断是一期还是二期
        public String judge(String remark){
            String re = null;
            if (remark.equals("1")) {
                re = "一期";
            } else if (remark.equals("2")) {
                re = "二期";
            }
            return re;
        }

    }
