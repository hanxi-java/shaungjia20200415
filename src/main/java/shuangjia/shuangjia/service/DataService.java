package shuangjia.shuangjia.service;

import shuangjia.shuangjia.entities.Data;

import java.text.ParseException;
import java.util.List;

public interface DataService {

    int insertDate(List<Data> list);

    List<Data> getHourData(String dataFormat) throws ParseException;
}
