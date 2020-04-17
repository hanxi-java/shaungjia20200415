package shuangjia.shuangjia.service;

import org.springframework.stereotype.Service;
import shuangjia.shuangjia.dao.AlarmDataDao;
import shuangjia.shuangjia.entities.AlarmData;
import shuangjia.shuangjia.entities.Paging;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AlarmDataServiceImpl implements AlarmDataService {
    @Resource
    private AlarmDataDao alarmDataDao;

    Date dateBegin = null;
    Date dateEnd = null;

    @Override
    public List<AlarmData> getAll(AlarmData alarmData) throws ParseException {
        Integer index = Integer.parseInt(alarmData.getIndex());
        Integer pageSize = Integer.parseInt(alarmData.getPageSize());
        Integer beginIndex = pageSize * (index - 1);
        System.out.println(alarmData.getDealTime());
        if (alarmData.getDealTime()!=null && alarmData.getDealTime()!="") {
            String dealTime = alarmData.getDealTime();
            String dealTImeBegin = dealTime.substring(0, dealTime.indexOf("%"));
            String dealTImeBeginEnd = dealTime.substring(dealTime.indexOf("%") + 1);

            //将字符串转化为日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateBegin = sdf.parse(dealTImeBegin);
            dateEnd = sdf.parse(dealTImeBeginEnd);

        }
        return alarmDataDao.getAll(beginIndex, pageSize, alarmData, dateBegin, dateEnd);
    }

    @Override
    public List<AlarmData> getAllWithoutCondition() {
        return alarmDataDao.getAllWithoutCondition();
    }

    //获取报警的数量
    public Integer getTotal(AlarmData alarmData) throws ParseException {

        if (alarmData.getDealTime()!=null && alarmData.getDealTime()!="") {
            String dealTime = alarmData.getDealTime();
            String dealTImeBegin = dealTime.substring(0, dealTime.indexOf("%"));
            String dealTImeBeginEnd = dealTime.substring(dealTime.indexOf("%") + 1);

            //将字符串转化为日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            dateBegin = sdf.parse(dealTImeBegin);
            dateEnd = sdf.parse(dealTImeBeginEnd);

        }

        return alarmDataDao.getTotal(alarmData, dateBegin, dateEnd);
    }

    //将重组得到的数据的插入数据库（tb_alarm_data）
    @Override
    public int insert(List<AlarmData> alarmDatas) {
        List<AlarmData> alarmDataList = alarmDataDao.getAllWithoutCondition();
        if (alarmDataList.size() == 0) {
            return alarmDataDao.insert(alarmDatas);
        } else {
            alarmDataDao.deleteAll();
            return alarmDataDao.insert(alarmDatas);
        }
    }
}
