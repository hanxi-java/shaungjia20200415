package shuangjia.shuangjia.controller;

import lombok.extern.slf4j.Slf4j;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import shuangjia.shuangjia.entities.*;
import shuangjia.shuangjia.service.DevicePropertyService;
import shuangjia.shuangjia.util.ExcelUtils;
import shuangjia.shuangjia.util.LoginRequired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@Slf4j
public class DataController {

    @Autowired
    private DevicePropertyService devicePropertyService;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");



    @GetMapping(value = "/aa")
public  void  inse()//exec添加数据
{
    ExcelUtils eu =new ExcelUtils();
    List<addmodl> molist =eu.readExcel("D:/新建LSX工作表.xlsx");
    int t=0;
    for(addmodl ed: molist){
        devicePropertyService.insertdecice(ed);
    }

}
    @GetMapping(value = "/deviceproperty/getbydeviceid/{deviceId}")
    public CommonResult getByDeviceId(@PathVariable("deviceId") Integer deviceId) {
        List<DeviceProperty>  devicePropertyList  = devicePropertyService.getByDeviceId(deviceId);
        if (devicePropertyList !=null) {
            log.info("****查询设备信息成功*********");
            return new CommonResult("200", "查询成功", devicePropertyList);
        }else{
            log.error("******查询设备信息失败*******");
            return new CommonResult("444", "查询失败", null);
        }
    }


    
    @GetMapping(value = "/deviceproperty/getall/{remark}")
    public CommonResult  getAll(@PathVariable("remark") String remark) {
            String result = judge(remark);
            String ch="all";
           List<Device> ec=devicePropertyService.getdevicealldata(result,ch);
        if (!ec.isEmpty()) {
            log.info("查询"+result+"中央屏幕数据成功");
            return new CommonResult("200", "查询成功", ec);
        }else{
            log.error("******查询中央屏幕数据失败******");
            return  null;
        }
    }
    @GetMapping(value = "/deviceproperty/getRight/{remark}")
    public CommonResult  getRight(@PathVariable("remark") String remark) {
        String result = judge(remark);
        String ch="right";
        List<Device> ec=devicePropertyService.getdevicealldata(result,ch);
        List<Device> list = new ArrayList();
        for (Device device : ec) {
            if(device.getDevicetypedatamodel()!=null){
                list.add(device);
            }
        }

        if (!ec.isEmpty()) {
            log.info("查询"+result+"中央屏幕数据成功");
            return new CommonResult("200", "查询成功", list);
        }else{
            log.error("******查询中央屏幕数据失败******");
            return  null;
        }
    }
    @GetMapping(value = "/deviceproperty/getLeft/{remark}")
    public CommonResult  getLeft(@PathVariable("remark") String remark) {
        String result = judge(remark);
        String ch="left";
        List<Device> ec=devicePropertyService.getdevicealldata(result,ch);
        if (!ec.isEmpty()) {
            log.info("查询"+result+"中央屏幕数据成功");
            return new CommonResult("200", "查询成功", ec);
        }else{
            log.error("******查询中央屏幕数据失败******");
            return  null;
        }
    }

    @GetMapping(value = "/deviceproperty/all")
    public CommonResult all() {



    return new CommonResult("200", "查询成功",null);
        }


    //@LoginRequired
    @GetMapping(value = "/deviceproperty/getbylevel/{remark}")
    public CommonResult getByLevel(@PathVariable("remark") String remark) {
        String result = judge(remark);
        List<DeviceProperty> devicePropertyList  = devicePropertyService.getByLevel(result);
        if (!devicePropertyList.isEmpty()) {
            log.info("********查询"+result+"液位数据成功*********");
            return new CommonResult("200", "查询成功", devicePropertyList);
        }else{
            log.error("******查询液位数据失败******");
            return new CommonResult("444", "查询失败", null);
        }
    }

    //  获取流量,remark表示一期二期
    @GetMapping(value = "/deviceproperty/getflow/{remark}")
    public CommonResult getFlow(@PathVariable("remark") String remark) {
        String result = judge(remark);
        List<DeviceProperty> devicePropertyList = devicePropertyService.getFlow(result);
        if (!devicePropertyList.isEmpty()) {
            log.info("****查询"+result+"流量数据成功*********");
            return new CommonResult("200", "查询成功", devicePropertyList);
        } else {
            log.error("******查询流量数据失败******");
            return new CommonResult("444", "查询失败", null);
        }
    }

    //  获取浊度,remark表示一期二期
    @GetMapping(value = "/deviceproperty/getturbidity/{remark}")
    public CommonResult getTurbidity(@PathVariable("remark") String remark) {
        String result = judge(remark);
        List<DeviceProperty> devicePropertyList = devicePropertyService.getTurbidity(result);
        if (!devicePropertyList.isEmpty()) {
            log.info("****查询"+result+"浊度数据成功*********");
            return new CommonResult("200", "查询成功", devicePropertyList);
        } else {
            log.error("******查询浊度数据失败******");
            return new CommonResult("444", "查询失败", null);
        }
    }
    /*都江堰数据接口*/
    @GetMapping(value = "/deviceproperty/getMonitorDu")
        public CommonResult getMonitorDu(){
        List<Devicetypedatamodel> devicetypedatamodelList=devicePropertyService.getMonitorDu();
        if (!devicetypedatamodelList.isEmpty()) {
            log.info("****查询都江堰监控数据成功*********");
            return new CommonResult("200", "查询成功", devicetypedatamodelList);
        } else {
            log.error("******查询都江堰监控数据成功******");
            return new CommonResult("444", "查询失败", null);
        }
    }

    //  获取浊度,remark表示一期二期
  /*  @GetMapping(value = "/deviceproperty/getCentral/{remark}")
    public Object getCentral(@PathVariable("remark") String remark) {
        String result = judge(remark);
        JSONArray json= new JSONArray();
        List<DeviceProperty> devicePropertyList = devicePropertyService.getCentral(result);
        if (!devicePropertyList.isEmpty()) {
            log.info("****查询"+result+"中央屏幕数据成功*********");
            return new CommonResult("200", "查询成功", devicePropertyList);
        } else {
            log.error("******查询中央屏幕数据失败******");
            return null;
        }
    }*/
    //判断是一期还是二期
    public String judge(String remark) {
        String re = null;
        if (remark.equals("1")) {
            re = "一期";
        } else if (remark.equals("2")) {
            re = "二期";
        }
        return re;
    }
}