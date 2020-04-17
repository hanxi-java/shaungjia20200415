package shuangjia.shuangjia.service;
import org.apache.ibatis.annotations.Param;
import shuangjia.shuangjia.entities.Device;
import shuangjia.shuangjia.entities.DeviceProperty;
import shuangjia.shuangjia.entities.Devicetypedatamodel;
import shuangjia.shuangjia.entities.addmodl;
import shuangjia.shuangjia.util.ExcelUtils;

import java.util.List;
/**
*@Author
*@Description 设备属性service接口
 *
*/
public interface DevicePropertyService  {

    int insert(DeviceProperty deviceProperty);
    int insertdecice(addmodl mo);

    List<DeviceProperty> getByDeviceId(int deviceId);

    List<DeviceProperty> getAll();

    int update( List<DeviceProperty> list);


    List<DeviceProperty> getByLevel(String remark);
    //获取中央图表数据
    List<DeviceProperty> getCentral(@Param("remark") String remark);

    //  获取流量,remark表示一期二期
    List<DeviceProperty> getFlow(String remark);

    //  获取浊度,remark表示一期二期
    List<DeviceProperty> getTurbidity(String remark);
    List<Device> getdevicealldata(String remark,String ch);

    //插入历史数据监控表
    int insertData(List<DeviceProperty> list);

    //根据id获取DeviceProperty对象
    DeviceProperty getById(Integer id);
    List<Devicetypedatamodel> getMonitorDu();
}
