package shuangjia.shuangjia.service;

import org.springframework.stereotype.Service;
import shuangjia.shuangjia.dao.DataTypeDao;
import shuangjia.shuangjia.dao.DayDataDao;
import shuangjia.shuangjia.dao.HourDataDao;
import shuangjia.shuangjia.entities.*;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DayDataServiceImpl implements DayDataService {
    @Resource
    private DayDataDao dayDataDao;
    @Resource
    private HourDataDao hourDataDao;
    @Resource
    private DeviceService deviceService;
    @Resource
    private DataTypeService dataTypeService;

    //从tb_report_hour_data表中得到每天的数据（DayData）
    @Override
    public List<DayData> getDayData(String endDateFormat) throws ParseException {
        //对日期进行格式化
       DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        //将当前时间减去一天，得到统计的起始时间
        Calendar rightNow = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = sdf.parse(endDateFormat);
        rightNow.setTime(date);
        rightNow.add(Calendar.DATE, -1);
        Date beginDate = rightNow.getTime();
        String beginDateFormat = df.format(beginDate);

        //从数据库中获取设备（device）和数据类型（dataType）的完整列表
        List<Device> deviceList = deviceService.getAll();
        List<DataType> dataTypeList = dataTypeService.getAll();

        //从tb_report_hour_data表中得到每天的数据，按照每天的起止时间进行截取
        beginDateFormat = beginDateFormat.substring(0, 10);
        List<HourData> hourDataList = hourDataDao.getDayData(beginDateFormat);

        //对小时数据按照设备（device）和数据类型（dataType）进行重组

        List<DayData> dayDataList = new ArrayList<>();

        for (Device device : deviceList) {
            for (DataType dataType : dataTypeList) {
                DayData dayData = new DayData();
                dayData.setDeviceId(device.getId());
                dayData.setDeviceName(device.getName());
                dayData.setStage(device.getRemark());
                //计算平均值
                float sumValue = 0f; //数据的和
                float avgValue = 0f; //数据的平均值
                float minValue = 0f; //数据的最小值
                float maxValue = 0f; //数据的最大值
                //定义存放平均值数据的集合
                List<Float> avgValueList = new ArrayList<>();
                //定义存放最大最小数据的集合
                List<Float> valueList = new ArrayList<>();
                for (HourData data : hourDataList) {
                    if (device.getId() == (data.getDeviceId()) &&
                            (dataType.getId()) == (data.getDataTypeId())) {
                        sumValue = sumValue + Float.parseFloat(data.getAvgValue());
                        avgValueList.add(Float.parseFloat(data.getAvgValue()));
                        valueList.add(Float.parseFloat(data.getMaxValue()));
                        valueList.add(Float.parseFloat(data.getMinValue()));
                        //设置数据类型
                        dayData.setDataTypeId(dataType.getId());
                        dayData.setDataTypeName(dataType.getDataTypeName());
                        //设置单位
                        dayData.setDataUnit(data.getDataUnit());
                        //处理日期字符串
                        String collectDate = beginDateFormat.substring(0, 10);
                        dayData.setTimePoint(collectDate);
                    }
                }
                //计算出平均值、最小值、最大值
                if (avgValueList.size() > 0) {
                    avgValue = sumValue / (avgValueList.size());
                    minValue = Collections.min(valueList);
                    maxValue = Collections.max(valueList);
                    dayData.setAvgValue(String.valueOf(avgValue));
                    dayData.setMinValue(String.valueOf(minValue));
                    dayData.setMaxValue(String.valueOf(maxValue));
                }

                if (dayData.getAvgValue() != null) {
                    dayDataList.add(dayData);
                }

            }
        }
        return dayDataList;
    }

    // 将每天生成的数据按照不同的设备、类型汇总，插入到每天汇总表（tb_report_day_data）中。
    @Override
    public int insert(List<DayData> monthDataList) {
        return dayDataDao.insert(monthDataList);
    }



    //从tb_report_day_data表中得到日报数据，实现多条件动态查询
    @Override
    public List<DayData> queryDayDataList(DayData dayData) {
        List<DayData>  dayDataList=dayDataDao.queryDayDataList(dayData);
        return dayDataList;
    }


}
