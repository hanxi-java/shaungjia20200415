package shuangjia.shuangjia.service;

import org.springframework.stereotype.Service;
import shuangjia.shuangjia.dao.DataDao;
import shuangjia.shuangjia.entities.Data;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class DataServiceImpl implements DataService {
    @Resource
    private DataDao dataDao;
    @Override
    public int insertDate(List<Data> list) {
        return dataDao.insertData(list);
    }

    @Override
    public List<Data> getHourData(String endDataFormat) throws ParseException {
        //定义开始时间字符串（beginTime）
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //得到标准格式的当前日期,当前日期为查询的结尾日期
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//对日期进行格式化
        Calendar rightNow = Calendar.getInstance();
        Date endTime=simpleDateFormat.parse(endDataFormat);
        rightNow.setTime(endTime);
        //将当前时间减去一小时，得到统计的起始时间
        rightNow.add(Calendar.HOUR, -1);
        Date beginDate = rightNow.getTime();
        String beginDateFormat = df.format(beginDate);


        //获取指定每个小时内的所有数据
        List<Data> dataList=dataDao.getDatasPerHour(beginDateFormat, endDataFormat);

        return dataList;
    }
}
