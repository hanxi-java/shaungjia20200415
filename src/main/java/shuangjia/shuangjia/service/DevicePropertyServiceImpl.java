package shuangjia.shuangjia.service;

import org.springframework.stereotype.Service;
import shuangjia.shuangjia.dao.DevicePropertyDao;
import shuangjia.shuangjia.entities.*;
import shuangjia.shuangjia.util.ExcelUtils;

import javax.annotation.Resource;
import java.util.*;
/**
*@Author
*@Description 设备属性业务处理层
 *
*/

@Service
public class DevicePropertyServiceImpl implements DevicePropertyService {
    @Resource
    private DevicePropertyDao devicePropertyDao;


    @Override
    public int insert(DeviceProperty deviceProperty) {

        return devicePropertyDao.insert(deviceProperty);
    }
    @Override
    public int insertdecice(addmodl mo) {
        int t=0;
        t=devicePropertyDao.insertdecice(mo);
        return t;
    }
    @Override
    public List<DeviceProperty> getByDeviceId(int deviceId) {
        return devicePropertyDao.getByDeviceId(deviceId);
    }

    @Override
    public List<DeviceProperty> getAll() {

        return devicePropertyDao.getAll();
    }

    @Override
    public int update(List<DeviceProperty> list) {
        return devicePropertyDao.update(list);
    }

    @Override
    public List<DeviceProperty> getByLevel(String remark) {
        List<DeviceProperty> list = devicePropertyDao.getByLevel(remark);
        return list;
    }

    @Override
    public List<DeviceProperty> getCentral(String remark) {
        List<DeviceProperty> list = devicePropertyDao.getCentral(remark);
        return list;
    }

    //  获取流量,remark表示一期二期
    @Override
    public List<DeviceProperty> getFlow(String remark) {
        List<DeviceProperty> list = devicePropertyDao.getFlow(remark);
        return list;
    }

    //  获取浊度,remark表示一期二期
    @Override
    public List<DeviceProperty> getTurbidity(String remark) {
        List<DeviceProperty> list = devicePropertyDao.getTurbidity(remark);
        return list;
    }

    @Override
    public List<Device> getdevicealldata(String remark,String ch) {
        //List<>
        List<Device> list1 = devicePropertyDao.getdevice(remark);
        List<Devicetypedatamodel> list2 = devicePropertyDao.getdevicetypedate(remark);
        List<Devicetypedatamodel> devlist = new ArrayList<Devicetypedatamodel>();
        //对设备进行遍历
        for (Device de : list1) {
            //通过对设备属性遍历的方法，向每个设备匹配属性（DeviceProperty)
            for (Devicetypedatamodel ddm : list2) {
                if (String.valueOf(de.getDeviceId()).equals(String.valueOf(ddm.getDeviceId()))) {

                    if (ch.equals("all")) {
                        devlist = devicePropertyDao.getdeviceById(ddm.getDeviceId());
                        de.setDevicetypedatamodel(devlist);
                    } else if (ch.equals("right")) {
                        devlist = devicePropertyDao.getdeviceById1(ddm.getDeviceId());
                        if(devlist.size()!=0){
                            de.setDevicetypedatamodel(devlist);
                        }
                    } else if (ch.equals("left")) {
                        devlist = devicePropertyDao.getdeviceById2(ddm.getDeviceId());
                        de.setDevicetypedatamodel(devlist);
                    }
                }
            }
        }
        return list1;
    }
    @Override
    public int insertData(List<DeviceProperty> list) {
        return devicePropertyDao.insertData(list);
    }

    //根据id获取DeviceProperty对象
    @Override
    public DeviceProperty getById(Integer id) {
        return devicePropertyDao.getById(id);
    }

    @Override
    public List<Devicetypedatamodel> getMonitorDu() {
        return devicePropertyDao.getMonitorDu();
    }


}