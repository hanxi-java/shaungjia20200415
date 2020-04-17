package shuangjia.shuangjia.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.annotation.Schedules;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import shuangjia.shuangjia.dao.DataDao;
import shuangjia.shuangjia.dao.DevicePropertyDao;
import shuangjia.shuangjia.dao.HourDataDao;
import shuangjia.shuangjia.entities.*;
import shuangjia.shuangjia.service.*;
import shuangjia.shuangjia.util.DataHandleUtil;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
@CrossOrigin
@RestController
public class HourDataController {
    @Autowired
    private DataService dataService;
    @Resource
    private DataDao dataDao;
    @Resource
    private DevicePropertyService devicePropertyService;
    @Resource
    private DeviceService deviceService;
    @Resource
    private DataTypeService dataTypeService;
    @Resource
    private HourDataService hourDataService;


    /**
     * @Author:韩熙
     * 按照小时提取数据，并将数据加入按照小时汇总的表中。
     */
    @Scheduled(cron = "0 30 0-23 * * *")
    public void getHourData() throws ParseException {
        //得到标准格式的当前日期,当前日期为查询的结尾日期
        DateFormat df= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//对日期进行格式化
        String endDateFormat = df.format(new Date());
        System.out.println(endDateFormat);
        //将当前时间减去一小时，得到统计的起始时间
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(new Date());
        rightNow.add(Calendar.HOUR, -1);
        Date beginDate = rightNow.getTime();
        String beginDateFormat = df.format(beginDate);

        //查询指定时间段的data数据
        List<Data> dataList = dataService.getHourData(beginDateFormat);
        long millis1 = System.currentTimeMillis();
        //遍历dataList,对其关联的对象进行赋值
        for (Data data : dataList) {
            //data中含有devicePropertyId属性，对应DeviceProperty对象的id属性,可以得到对应的DeviceProperty对象
            DeviceProperty deviceProperty = devicePropertyService.getById(data.getDevice_property_id());
            //deviceProperty中含有dataTypeId属性，对应Device对象的id属性,可以得到对应的device对象
            Device device = deviceService.getDeviceByid(deviceProperty.getDeviceId());
            //deviceProperty中含有deviceId属性，对应DataType对象的id属性,可以得到对应的dataType对象
            DataType dataType = dataTypeService.getDataTypeById(deviceProperty.getDataTypeId());
            //将得到的设备属性、设备、数据类型信息存入到data对象对应的属性中
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
        List<HourData> hourDataList = dataTypeService.dataHandler(dataList,deviceList,dataTypeList);
        //测试运行时间
        long millis2 = System.currentTimeMillis();
        System.out.println(millis2 - millis1);

        //将得到的数据插入按小时汇总的表（ tb_report_hour_data）
        if (hourDataList.size()>0) {
            Integer result=hourDataService.insert(hourDataList);
        }
    }
}
