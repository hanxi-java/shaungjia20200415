package shuangjia.shuangjia.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import shuangjia.shuangjia.entities.Data;
import shuangjia.shuangjia.entities.DataType;
import shuangjia.shuangjia.entities.Device;
import shuangjia.shuangjia.entities.HourData;
import shuangjia.shuangjia.service.DataTypeService;
import shuangjia.shuangjia.service.DataTypeServiceImpl;
import shuangjia.shuangjia.service.DeviceService;
import shuangjia.shuangjia.service.DeviceServiceImpl;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DataHandleUtil {
    public List<HourData> dataHandler(List<Data> dataList, List<Device> deviceList,
                                      List<DataType> dataTypeList) throws ParseException {
        List<HourData> hourDataList = new ArrayList<>();
        for (Device device : deviceList) {
            for (DataType dataType : dataTypeList) {
                HourData hourData = new HourData();
                hourData.setDeviceId(device.getId());
                hourData.setDeviceName(device.getName());
                hourData.setStage(device.getRemark());
                //计算平均值
                float sumValue = 0f; //数据的和
                float avgValue = 0f; //数据的平均值
                float minValue = 0f; //数据的最小值
                float maxValue = 0f; //数据的最大值
                //定义存放数据的集合
                List<Float> collectValueList = new ArrayList<>();

                for (Data data : dataList) {
                    if (data.getDevice() != null && data.getDataType() != null &&
                            device.getId() == (data.getDevice().getId()) &&
                            (dataType.getId()) == (data.getDataType().getId())) {

                        sumValue = sumValue + Float.parseFloat(data.getCollect_value());
                        collectValueList.add(Float.parseFloat(data.getCollect_value()));
                        //设置数据类型
                        hourData.setDataTypeId(dataType.getId());
                        hourData.setDataTypeName(dataType.getDataTypeName());
                        //设置单位
                        hourData.setDataUnit(data.getDeviceProperty().getDataUnit());

                    }
                }
                //得到日期字符串，collectDate为结束统计的时间
                String collectDate = dataList.get(dataList.size()-1).getCollect_date();
                //处理日期字符串
                collectDate = collectDate.substring(0, collectDate.indexOf(":")) + ":30:00";

               // 从字符串提取出日期
                SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                //得到标准格式的当前日期,当前日期为查询的结尾日期
                Calendar rightNow = Calendar.getInstance();
                Date endTime=simpleDateFormat.parse(collectDate);
                rightNow.setTime(endTime);
                //将结束时间减去一小时，得到统计的起始时间
                rightNow.add(Calendar.HOUR, -1);
                Date beginDate = rightNow.getTime();
                // 从日期转化为标准格式的字符串
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//对日期进行格式化
                String beginDateFormat = df.format(beginDate);
                String hour = collectDate.substring(collectDate.indexOf(":") - 2, collectDate.indexOf(":"));
                hourData.setTimePoint(hour+"时");
                hourData.setReportHour(beginDateFormat +"——"+collectDate);


                //计算出平均值、最小值、最大值
                if (collectValueList.size()> 0) {
                    avgValue = sumValue / (collectValueList.size());
                    minValue = Collections.min(collectValueList);
                    maxValue = Collections.max(collectValueList);
                    hourData.setAvgValue(String.valueOf(avgValue));
                    hourData.setMinValue(String.valueOf(minValue));
                    hourData.setMaxValue(String.valueOf(maxValue));
                }
                if (hourData.getAvgValue() != null) {

                    hourDataList.add(hourData);
                }
            }
        }
        return hourDataList;
    }
}

