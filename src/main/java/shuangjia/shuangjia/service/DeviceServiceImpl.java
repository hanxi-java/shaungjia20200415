package shuangjia.shuangjia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import shuangjia.shuangjia.dao.DeviceDao;
import shuangjia.shuangjia.entities.Device;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DeviceServiceImpl implements DeviceService{
    @Resource
    private DeviceDao deviceDao;
    @Override
    public Device getDeviceByid(Integer id) {
        return deviceDao.getDeviceByid(id);
    }

    @Override
    public List<Device> getAll() {
        return deviceDao.getAll();
    }

    //根据一期或二期向页面返回设备列表
    @Override
    public List<Device> getByStage(String stage) {
        String stageName = null;
        if (stage.equals("1")) {
            stageName = "一期";
        }
        if (stage.equals("2")) {
            stageName = "二期";
        }
        return deviceDao.getByStageName(stageName);
    }

}
