package shuangjia.shuangjia.service;

import org.springframework.stereotype.Service;
import shuangjia.shuangjia.dao.DayDataDao;
import shuangjia.shuangjia.dao.MonthDataDao;
import shuangjia.shuangjia.entities.*;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class MonthDataServiceImpl implements MonthdataService {
    @Resource
    private DeviceService deviceService;
    @Resource
    private DataTypeService dataTypeService;
    @Resource
    private DayDataDao dayDataDao;

    @Resource
    private MonthDataDao monthDataDao;
    @Override
    public List<MonthData> getMonthData(String endMonthFormat) throws ParseException {

        //对日期进行格式化
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");

        //将当前时间减去一个月，得到统计的起始时间
        Calendar rightNow = Calendar.getInstance();
//        rightNow.setTime(new Date());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date month = sdf.parse(endMonthFormat);
        rightNow.setTime(month);
        rightNow.add(Calendar.MONTH, -1);
        Date beginMonth = rightNow.getTime();
        String beginMonthFormat = df.format(beginMonth);

        //从数据库中获取设备（device）和数据类型（dataType）的完整列表
        List<Device> deviceList = deviceService.getAll();
        List<DataType> dataTypeList = dataTypeService.getAll();

        //从tb_report_day_data表中得到每天的数据，按月进行汇总
        beginMonthFormat = beginMonthFormat.substring(0, 7);
        List<DayData> dayDataList = dayDataDao.getMonthData(beginMonthFormat);

        //对天数据按照设备（device）和数据类型（dataType）进行重组

        List<MonthData> monthDataList = new ArrayList<>();

        for (Device device : deviceList) {
            for (DataType dataType : dataTypeList) {
                MonthData monthData = new MonthData();
                monthData.setDeviceId(device.getId());
                monthData.setDeviceName(device.getName());
                monthData.setStage(device.getRemark());
                //计算平均值
                float sumValue = 0f; //数据的和
                float avgValue = 0f; //数据的平均值
                float minValue = 0f; //数据的最小值
                float maxValue = 0f; //数据的最大值
                //定义存放平均值数据的集合
                List<Float> avgValueList = new ArrayList<>();
                //定义存放最大最小数据的集合
                List<Float> valueList = new ArrayList<>();
                for (DayData dayData : dayDataList) {
                    if (device.getId() == (dayData.getDeviceId()) &&
                            (dataType.getId()) == (dayData.getDataTypeId())) {
                        sumValue = sumValue + Float.parseFloat(dayData.getAvgValue());
                        avgValueList.add(Float.parseFloat(dayData.getAvgValue()));
                        valueList.add(Float.parseFloat(dayData.getMaxValue()));
                        valueList.add(Float.parseFloat(dayData.getMinValue()));
                        //设置数据类型
                        monthData.setDataTypeId(dataType.getId());
                        monthData.setDataTypeName(dataType.getDataTypeName());
                        //设置单位
                        monthData.setDataUnit(dayData.getDataUnit());
                        //处理日期字符串
                        String collectDate = beginMonthFormat.substring(0, 7);
                        monthData.setTimePoint(collectDate);
                    }
                }
                //计算出平均值、最小值、最大值
                if (avgValueList.size() > 0) {
                    avgValue = sumValue / (avgValueList.size());
                    minValue = Collections.min(valueList);
                    maxValue = Collections.max(valueList);
                    monthData.setAvgValue(String.valueOf(avgValue));
                    monthData.setMinValue(String.valueOf(minValue));
                    monthData.setMaxValue(String.valueOf(maxValue));
                }

                if (monthData.getAvgValue() != null) {
                    monthDataList.add(monthData);
                }

            }
        }
        return monthDataList;
    }
    //插入按月生成的数据
    @Override
    public int insert(List<MonthData> monthDataList) {
        return monthDataDao.insert(monthDataList);
    }


    //获取年报数据
    @Override
    public List<MonthData> queryMonthDataList(MonthData monthData) {
        return monthDataDao.queryMonthDataList(monthData);
    }

}
