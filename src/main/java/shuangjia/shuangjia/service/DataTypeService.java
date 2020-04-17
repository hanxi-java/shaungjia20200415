package shuangjia.shuangjia.service;

import shuangjia.shuangjia.entities.DataType;

import java.util.List;

public interface DataTypeService {

    DataType getDataTypeById(Integer id);

    List<DataType> getAll();
}
