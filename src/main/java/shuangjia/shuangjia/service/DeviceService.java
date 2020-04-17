package shuangjia.shuangjia.service;

import shuangjia.shuangjia.entities.Device;

import java.util.List;

public interface DeviceService {

    Device getDeviceByid(Integer id);

    List<Device> getAll();

    //根据一期或二期向页面返回设备列表
    List<Device> getByStage(String stage);
}
