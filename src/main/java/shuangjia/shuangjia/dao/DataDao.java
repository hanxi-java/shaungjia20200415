package shuangjia.shuangjia.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import shuangjia.shuangjia.entities.Data;
import java.util.List;

@Mapper
public interface DataDao {
    int insertData(List<Data> list);

    //获取指定每个小时内的所有数据
    List<Data> getDatasPerHour(@Param("beginDateFormat") String beginDateFormat,
                               @Param("endDataFormat") String endDataFormat);
}
