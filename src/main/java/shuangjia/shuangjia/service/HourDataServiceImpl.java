package shuangjia.shuangjia.service;

import org.springframework.stereotype.Service;
import shuangjia.shuangjia.dao.HourDataDao;
import shuangjia.shuangjia.entities.HourData;

import javax.annotation.Resource;
import java.util.List;
@Service
public class HourDataServiceImpl implements HourDataService {
    @Resource
    private HourDataDao hourDataDao;

    /**
     * 向tb_report_hour_data表中插入hourDataList数据，hourDataList是按数据类型、设备、
     * 小时汇总生成的数据
     * */
    @Override
    public int insert(List<HourData> hourDataList) {
        return hourDataDao.insert(hourDataList);
    }



    @Override
    public List<HourData> getDayReportData(String stage, Integer deviceId,
                                           String reportDate, Integer dataTypeId) {
        return hourDataDao.getDayReportData(stage, deviceId, reportDate, dataTypeId);
    }


    /**
     * 获取日报数据，日报的数据实际上是tb_report_hour_data表中的hourDataList，
     * 传入对象参数可实现多条件动态查询
     * */
    @Override
    public List<HourData> queryHourDataList(HourData reportHourData) {
        List<HourData> hourDataList=hourDataDao.queryHourDataList(reportHourData);
        return hourDataList;
    }


}
