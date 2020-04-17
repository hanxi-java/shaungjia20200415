package shuangjia.shuangjia.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import shuangjia.shuangjia.entities.Device;

import java.util.List;

@Mapper
public interface DeviceDao {

    Device getDeviceByid(Integer id);

    List<Device> getAll();

    //根据一期或二期向页面返回设备列表
    List<Device> getByStageName(@Param("stageName") String stageName);
}
