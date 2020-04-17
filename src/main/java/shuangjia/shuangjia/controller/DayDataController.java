package shuangjia.shuangjia.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import shuangjia.shuangjia.entities.*;
import shuangjia.shuangjia.service.DataTypeService;
import shuangjia.shuangjia.service.DayDataService;
import shuangjia.shuangjia.service.DeviceService;
import shuangjia.shuangjia.service.HourDataService;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin
@RestController
public class DayDataController {

    @Resource
    private DayDataService dayDataService;
    @Resource
    private HourDataService hourDataService;

    /**
     * @Author:韩熙 从小时汇总表(tb_report_hour_data)中提取数据，并将每天生成的数据按照不同的设备、类型汇总，
     * 插入到每天汇总表（tb_report_day_data）中。
     * cron表达式意义：每天的00:30分运行该方法
     */
    @Scheduled(cron = "0 30 0 1/1 * *")
    public void getDayData() throws ParseException {
        //得到标准格式的当前日期,当前日期为查询的结尾日期
        //对日期进行格式化
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endDateFormat = df.format(new Date());
        //从tb_report_hour_data表中得到每天的数据（DayData）
        List<DayData> dayDataList = dayDataService.getDayData(endDateFormat);
       // 将每天生成的数据按照不同的设备、类型汇总，插入到每天汇总表（tb_report_day_data）中。
        if (dayDataList.size() > 0) {
            Integer result = dayDataService.insert(dayDataList);
        }
    }


    //获取日报数据，日报的数据实际上是tb_report_hour_data表中的hourDataList
    @PostMapping("/getDailyReport")
    public List<HourData> getDayReportData(HttpServletRequest request) {
        HourData hourData = (HourData) request.getAttribute("hourData");
        //获取日报数据，日报的数据实际上是tb_report_hour_data表中的hourDataList
        List<HourData> hourDataList=hourDataService.queryHourDataList(hourData);
        return hourDataList;
    }
}