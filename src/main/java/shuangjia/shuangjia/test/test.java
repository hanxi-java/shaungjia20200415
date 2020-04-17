package shuangjia.shuangjia.test;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import shuangjia.shuangjia.dao.AlarmDefineDao;
import shuangjia.shuangjia.dao.DataDao;
import shuangjia.shuangjia.dao.HourDataDao;
import shuangjia.shuangjia.dao.TbAlarmDao;
import shuangjia.shuangjia.entities.*;
import shuangjia.shuangjia.service.*;
import shuangjia.shuangjia.util.DataHandleUtil;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class test {
    @Resource
    private DataDao dataDao;
    @Resource
    private DataService dataService;
    @Resource
    private DevicePropertyService devicePropertyService;
    @Resource
    private DeviceService deviceService;
    @Resource
    private DataTypeService dataTypeService;

    @Resource
    private HourDataService hourDataService;

    @Resource
    private HourDataDao hourDataDao;

    @Resource
    private DayDataService dayDataService;

    @Resource
    private MonthdataService monthdataService;

    @Resource
    private TbAlarmDao tbAlarmDao;

    @Resource
    private AlarmDefineDao alarmDefineDao;

    @Resource
    private TbAlarmService tbAlarmService;

    //统计小时数据
    @GetMapping("/test")
    public void test() throws ParseException {
        // 从字符串提取出日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //得到标准格式的当前日期,当前日期为查询的结尾日期
        Calendar rightNow = Calendar.getInstance();

        // 从日期转化为标准格式的字符串
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//对日期进行格式化
        String endDateFormat = "2020-04-05 01:30:00";
        for (int i = 1; ; i++) {

            Date endDate = simpleDateFormat.parse(endDateFormat);
            rightNow.setTime(endDate);
            //将结束时间减去一小时，得到统计的起始时间
            rightNow.add(Calendar.HOUR, +1);
            endDate = rightNow.getTime();
            endDateFormat = df.format(endDate);
            List<Data> dataList = dataService.getHourData(endDateFormat);
            long millis1 = System.currentTimeMillis();
            //遍历dataList,对其关联的对象进行赋值
            for (Data data : dataList) {
                //data中含有devicePropertyId属性，对应DeviceProperty对象的id属性,可以得到对应的DeviceProperty对象
                DeviceProperty deviceProperty = devicePropertyService.getById(data.getDevice_property_id());
                //deviceProperty中含有dataTypeId属性，对应Device对象的id属性,可以得到对应的device对象
                Device device = deviceService.getDeviceByid(deviceProperty.getDeviceId());
                //deviceProperty中含有deviceId属性，对应DataType对象的id属性,可以得到对应的dataType对象
                DataType dataType = dataTypeService.getDataTypeById(deviceProperty.getDataTypeId());
                //将得到的设备属性、设备、数据类型信息存入到data对象对应的属性中,便于后续操作
                data.setDeviceProperty(deviceProperty);
                data.setDevice(device);
                data.setDataType(dataType);
            }
            //从数据库中获取设备（device）和数据类型（dataType）的完整列表
            List<Device> deviceList = deviceService.getAll();
            List<DataType> dataTypeList = dataTypeService.getAll();

            //加入一个工具类DataHandleUtil,传入dataList、deviceList、dataTypeList作为参数，
            // 返回hourDataList集合
            DataHandleUtil dataTypeService = new DataHandleUtil();
            List<HourData> hourDataList = dataTypeService.dataHandler(dataList, deviceList, dataTypeList);

            long millis2 = System.currentTimeMillis();
            System.out.println(millis2 - millis1);
            //将得到的数据插入按小时汇总的表（ tb_report_hour_data）
            if (hourDataList.size() > 0) {
                Integer result = hourDataService.insert(hourDataList);
                System.out.println(result);
                System.out.println(hourDataList);
            }
        }
    }


    @GetMapping("/test1")
    public CommonResult test1() {
        //得到标准格式的当前日期,当前日期为查询的结尾日期
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//对日期进行格式化
        String endDateFormat = df.format(new Date());
        System.out.println(endDateFormat);
        //将当前时间减去一小时，得到统计的起始时间
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        rightNow.add(Calendar.DATE, -1);
        Date beginDate = rightNow.getTime();
        String beginDateFormat = df.format(beginDate);
        System.out.println(beginDateFormat + " : " + endDateFormat);

        List<HourData> list = hourDataService.getDayReportData("一期", 11, "2020-03-26", 8);
        if (list.size() > 0) {
            return new CommonResult("200", "查询成功", list);
        } else {
            return new CommonResult("444", "查询失败", null);
        }

    }

    //统计日数据
    @GetMapping("/test2")
    public void test2() throws ParseException {
        //得到标准格式的当前日期,当前日期为查询的结尾日期
        // 从字符串提取出日期
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //得到标准格式的当前日期,当前日期为查询的结尾日期
        Calendar rightNow = Calendar.getInstance();

        // 从日期转化为标准格式的字符串
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//对日期进行格式化
        String endDateFormat = "2020-04-01 00:30:00";
        for (int i = 1; ; i++) {
            Date endDate = simpleDateFormat.parse(endDateFormat);
            rightNow.setTime(endDate);
            rightNow.add(Calendar.DATE, +1);
            endDate = rightNow.getTime();
            endDateFormat = df.format(endDate);
            long millis1 = System.currentTimeMillis();
            //从tb_report_hour_data表中得到每天的数据（DayData）
            List<DayData> monthDataList = dayDataService.getDayData(endDateFormat);

            //将得到的数据插入按天汇总的表（ tb_report_day_data）
            if (monthDataList.size() > 0) {
                Integer result = dayDataService.insert(monthDataList);
                System.out.println(result + "1111");
                long millis2 = System.currentTimeMillis();
                System.out.println(millis2 - millis1);
                System.out.println(monthDataList);
            }
        }
    }

    @GetMapping("/test3")
    public List<HourData> test3() {
        HourData hourData = new HourData();
        hourData.setStage("一期");
        hourData.setDeviceId(3);
        String reportQueryDate = "2020-03-27";
        List<HourData> hourDataList = hourDataService.queryHourDataList(hourData);
        //将得到的数据插入按天汇总的表（ tb_report_day_data）
        if (hourDataList.size() > 0) {
            System.out.println(hourDataList);
        }
        return hourDataList;
    }

    //统计月数据
    @GetMapping("/test4")
    public void test4() throws ParseException {
        //得到标准格式的当前日期,当前日期为查询的结尾日期
        //对日期进行格式化
//        DateFormat df= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String endMonthFormat = "2020-04-01";
        //从tb_report_hour_data表中得到每天的数据（DayData）
        List<MonthData> monthDataList = monthdataService.getMonthData(endMonthFormat);

        //将得到的数据插入按天汇总的表（ tb_report_month_data）
        if (monthDataList.size() > 0) {
            Integer result = monthdataService.insert(monthDataList);
            System.out.println(result + "1111");
            System.out.println(monthDataList);
        }
    }

    @GetMapping("/get")
    public List<Tbalarm> get() {
        return tbAlarmDao.getAll();
    }

    @GetMapping("/get1")
    public AlarmDefine get1() {
        return alarmDefineDao.getAlarmDefineById(8);
    }

    @PostMapping("/get2")
    public List<AlarmData> get2(HttpServletRequest request) {
        String index=request.getParameter("index");
        System.out.println(request);
        List<AlarmData> list = tbAlarmService.getAlarmData();
        return list;
    }

   /* @GetMapping("/get3")
    public List<AlarmData> get3() {
            TimerTask timerTask = new TimerTask() {


                @Override
                public void run() {
                    System.out.println("task  run:"+ new Date());
                }

            Timer timer = new Timer();
            //安排指定的任务在指定的时间开始进行重复的固定延迟执行。这里是每3秒执行一次
            timer.schedule(timerTask,10,3000);
        }
    }*/
}
