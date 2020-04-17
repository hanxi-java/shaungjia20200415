package shuangjia.shuangjia.service;

import org.springframework.stereotype.Service;
import shuangjia.shuangjia.dao.DataTypeDao;
import shuangjia.shuangjia.entities.DataType;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DataTypeServiceImpl implements DataTypeService {
    @Resource
    private DataTypeDao dataTypeDao;
    @Override
    public DataType getDataTypeById(Integer id) {
        return dataTypeDao.getDataTypeById(id);
    }

    @Override
    public List<DataType> getAll() {
        return dataTypeDao.getAll();
    }

}
