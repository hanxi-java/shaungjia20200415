package shuangjia.shuangjia.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import shuangjia.shuangjia.entities.Device;
import shuangjia.shuangjia.entities.DeviceProperty;
import shuangjia.shuangjia.entities.Devicetypedatamodel;
import shuangjia.shuangjia.entities.addmodl;
import shuangjia.shuangjia.util.ExcelUtils;

import java.util.List;

@Mapper
public interface DevicePropertyDao {

    int insert(DeviceProperty deviceProperty);
    int insertdecice(addmodl mo);

    List<DeviceProperty> getByDeviceId(@Param("deviceId") int deviceId);

    List<DeviceProperty> getAll();

    int update( List<DeviceProperty> list);

    //根据id获取DeviceProperty对象
    DeviceProperty getById(@Param("id") Integer id);

    void test();
    //获取液位
    List<DeviceProperty> getByLevel(@Param("remark") String remark);

    //获取中央图表数据
    List<DeviceProperty> getCentral(@Param("remark") String remark);

    //  获取流量,remark表示一期二期
    List<DeviceProperty> getFlow(@Param("remark") String remark);

    //  获取浊度,remark表示一期二期
    List<DeviceProperty> getTurbidity(@Param("remark") String remark);

    List<Device> getdevice(@Param("remark") String remark);
    List<Devicetypedatamodel> getdevicetypedate(@Param("remark") String remark);

    //根据ID获取设备属性
    List<Devicetypedatamodel> getdeviceById(@Param("deviceId") int deviceId);
    //根据ID获取设备属性 左
    List<Devicetypedatamodel> getdeviceById1(@Param("deviceId") int deviceId);
    //根据ID获取设备属性 右
    List<Devicetypedatamodel> getdeviceById2(@Param("deviceId") int deviceId);
    //插入历史数据监控表
    int insertData(List<DeviceProperty> list);

    //根据ID获取设备属性
    DeviceProperty getDevicePropertyById(@Param("id") Integer id);

    //获取都江堰水厂监控数据
    List<Devicetypedatamodel> getMonitorDu();
}
