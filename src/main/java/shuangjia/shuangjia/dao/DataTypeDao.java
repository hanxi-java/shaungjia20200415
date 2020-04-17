package shuangjia.shuangjia.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import shuangjia.shuangjia.entities.DataType;

import java.util.List;

@Mapper
public interface DataTypeDao {

    DataType getDataTypeById(@Param("dataTypeId") Integer id);

    List<DataType> getAll();
}
