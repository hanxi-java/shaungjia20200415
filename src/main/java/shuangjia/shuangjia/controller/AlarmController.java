package shuangjia.shuangjia.controller;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;
import shuangjia.shuangjia.entities.*;
import shuangjia.shuangjia.service.AlarmDataService;
import shuangjia.shuangjia.service.AlarmServer;
import shuangjia.shuangjia.service.TbAlarmService;
import shuangjia.shuangjia.util.RecordExportUtil;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@CrossOrigin
@RestController
@Slf4j
public class AlarmController {
    @Autowired
    private AlarmServer AlarmServer;

    @Resource
    private AlarmDataService alarmDataService;

    @Resource
    private TbAlarmService tbAlarmService;


    /*设置报警参数*/
    @RequestMapping(value = "/Alarm/changealarmvalue", method = RequestMethod.POST)
    public CommonResult Updatealarmvalue(@RequestBody Alarmdevicemodel AlarmParameter) {

        int i = AlarmServer.Updatealarmvalue(AlarmParameter);
        if (i > 0) {
            return new CommonResult("200", "请求成功", null);
        }
        return new CommonResult("404", "请求失败", null);
    }

    /*水厂树形数据*/
    @RequestMapping(value = "/Alarm/gettree")
    public String gettree() {
        StringBuilder sb = new StringBuilder();
        String s = AlarmServer.gettree();

        if (s.length() > 0) {
            return s;
        } else {
            return s;
        }
    }

    /*根据deviceid获取数据类型*/
    @RequestMapping(value = "/Alarm/GetDevicePropertybydeviceid", method = RequestMethod.POST)
    public CommonResult GetDevicePropertybydeviceid(@RequestBody GetDevicePropertybydeviceid GetDevicePropertybydeviceid) {
        List<DeviceProperty> list = AlarmServer.GetDevicePropertybydeviceid(Integer.valueOf(GetDevicePropertybydeviceid.getDeviceid()));
        if (list.size() > 0) {
            return new CommonResult("200", "请求成功", list);
        }
        return new CommonResult("404", "请求失败", null);
    }

    /*查询故障分类*/
    @RequestMapping(value = "/Alarm/getFaultType")
    public CommonResult getFaultType() {
        List<FaultType> list = AlarmServer.getFaultType();
        if (list != null) {
            return new CommonResult("200", "请求成功", list);
        } else {
            return new CommonResult("404", "请求失败", null);
        }
    }

    /*查询报警类型*/
    @RequestMapping(value = "/Alarm/getAlarmType")
    public CommonResult getAlarmType() {
        List<AlarmType> list = AlarmServer.getAlarmType();
        if (true) {
            return new CommonResult("200", "请求成功", list);
        } else {
            return new CommonResult("404", "请求失败", null);
        }
    }

    /*修改报警类型*/
    @RequestMapping(value = "/Alarm/UpdateFaultType", method = RequestMethod.POST)
    public CommonResult UpdateFaultType(@RequestBody FaultType faultType) {
        int i = AlarmServer.UpdateFaultType(faultType);
        if (i > 0) {
            return new CommonResult("200", "请求成功", null);
        } else {
            return new CommonResult("404", "请求失败", null);
        }
    }

    /*添加报警类型*/
    @RequestMapping(value = "/Alarm/AddFaultType", method = RequestMethod.POST)
    public CommonResult AddFaultType(@RequestBody FaultType FaultType) {
        int i = AlarmServer.AddFaultType(FaultType);
        if (i > 0) {
            return new CommonResult("200", "请求成功", null);
        } else {
            return new CommonResult("404", "请求失败", null);
        }
    }

    /*删除报警类型*/
    @RequestMapping(value = "/Alarm/DeleteFaultType", method = RequestMethod.POST)
    public CommonResult DeleteFaultType(@RequestBody FaultType FaultType) {
        int i = AlarmServer.DelteFaultType(FaultType.getId());
        if (i > 0) {
            return new CommonResult("200", "请求成功", null);
        } else {
            return new CommonResult("404", "请求失败", null);
        }
    }

    /*查询等级*/
    @RequestMapping(value = "/Alarm/getgrade")
    public CommonResult getgrade() {
        List<grade> i = AlarmServer.getgrade();
        if (i.size() > 0) {
            return new CommonResult("200", "请求成功", i);
        } else {
            return new CommonResult("404", "请求失败", null);
        }
    }

    /*修改等级*/
    @RequestMapping(value = "/Alarm/Updategrade", method = RequestMethod.POST)
    public CommonResult Updategrade(@RequestBody grade frade) {
        int i = AlarmServer.Updategrade(frade);
        if (i > 0) {
            return new CommonResult("200", "请求成功", null);
        } else {
            return new CommonResult("404", "请求失败", null);
        }
    }

    /*添加等级*/
    @RequestMapping(value = "/Alarm/Addgrade", method = RequestMethod.POST)
    public CommonResult Addgrade(@RequestBody grade frade) {
        int i = AlarmServer.Addgrade(frade);
        if (i > 0) {
            return new CommonResult("200", "请求成功", null);
        } else {
            return new CommonResult("404", "请求失败", null);
        }
    }

    /*删除等级*/
    @RequestMapping(value = "/Alarm/Deletegrade", method = RequestMethod.POST)
    public CommonResult Deletegrade(@RequestBody grade frade) {
        int i = AlarmServer.Deltegrade(frade.getId());
        if (i > 0) {
            return new CommonResult("200", "请求成功", null);
        } else {
            return new CommonResult("404", "请求失败", null);
        }
    }

    /*查询故障定义*/
    @RequestMapping(value = "/Alarm/getAlarmDefine", method = RequestMethod.POST)
    public CommonResult getAlarmDefine(@RequestBody AlarmDefineWhereParmart Paging) {
        StringBuilder where = new StringBuilder();
        where.append("1=1");
        if (Paging.getName() != null && Paging.getName() != "") {
            where.append(" and a.name='");
            where.append(Paging.getName() + "'");
        }
        if (Paging.getAlarmTypeId() != null && Paging.getAlarmTypeId() != "") {
            where.append(" and a.alarmTypeId='");
            where.append(Integer.valueOf(Paging.getAlarmTypeId()) + "'");
        }
        if (Paging.getGradeId() != null && Paging.getGradeId() != "") {
            where.append(" and a.gradeId='");
            where.append(Integer.valueOf(Paging.getGradeId()) + "'");
        }
        if (Paging.getIsStop() != null && Paging.getIsStop() != "") {
            where.append(" and a.isStop='");
            where.append(Integer.valueOf(Paging.getIsStop()) + "'");
        }

        AlarmDefines list = AlarmServer.getAlarmDefine(Integer.valueOf(Paging.getIndex()), Integer.valueOf(Paging.getPageSize()), where.toString());
        if (list.alarmDefineList.size() > 0) {
            return new CommonResult("200", "请求成功", list);
        } else {
            return new CommonResult("404", "请求失败", null);
        }
    }

    /*根据条件查询故障定义*/
    @RequestMapping(value = "/Alarm/getAlarmDefineByWhere", method = RequestMethod.POST)
    public CommonResult getAlarmDefineByWhere(@RequestBody Paging Paging) {
        if (Paging.getWheres() == null) {
            Paging.setWheres("1=1");
        }
        AlarmDefines list = AlarmServer.getAlarmDefineByWhere(Integer.valueOf(Paging.getIndex()), Integer.valueOf(Paging.getPageSize()), Paging.getWheres());
        if (list.alarmDefineList.size() > 0) {
            return new CommonResult("200", "请求成功", list);
        } else {
            return new CommonResult("404", "请求失败", null);
        }
    }

    /*修改定义*/
    @RequestMapping(value = "/Alarm/UpdAlarmDefine", method = RequestMethod.POST)
    public CommonResult UpdAlarmDefine(@RequestBody AlarmDefine AlarmDefine) {
        int i = AlarmServer.UpdAlarmDefine(AlarmDefine);
        if (i > 0) {
            return new CommonResult("200", "请求成功", null);
        } else {
            return new CommonResult("404", "请求失败", null);
        }
    }

    /*添加故障定义*/
    @RequestMapping(value = "/Alarm/AddAlarmDefine", method = RequestMethod.POST)
    public CommonResult AddAlarmDefine(@RequestBody AlarmDefine AlarmDefine) {
        int i = AlarmServer.AddAlarmDefine(AlarmDefine);
        if (i > 0) {
            return new CommonResult("200", "请求成功", null);
        } else {
            return new CommonResult("404", "请求失败", null);
        }
    }

    /*删除故障定义*/
    @RequestMapping(value = "/Alarm/DeleteAlarmDefine", method = RequestMethod.POST)
    public CommonResult DeleteAlarmDefine(@RequestBody AlarmDefine AlarmDefine) {
        int i = AlarmServer.DeleteAlarmDefine(AlarmDefine.getId());
        if (i > 0) {
            return new CommonResult("200", "请求成功", null);
        } else {
            return new CommonResult("404", "请求失败", null);
        }
    }


    /*查询设备属性开关量定义*/
    @RequestMapping(value = "/Alarm/getDevicePropertyValueDefine")
    public CommonResult getDevicePropertyValueDefine() {
        List<DevicePropertyValueDefine> list = AlarmServer.getDevicePropertyValueDefine();
        if (list.size() > 0) {
            return new CommonResult("200", "请求成功", list);
        } else {
            return new CommonResult("404", "请求失败", null);
        }
    }

    /*根据设备id查询设备属性开关量定义*/
    @RequestMapping(value = "/Alarm/getDevicePropertyValueDefinebydeviceid", method = RequestMethod.POST)
    public CommonResult getDevicePropertyValueDefinebydeviceid(@RequestBody DevicePropertyValueDefine deviceid) {
        List<DevicePropertyValueDefine> list = AlarmServer.getDevicePropertyValueDefinebydeviceid(deviceid.getPropertyId());
        if (list.size() > 0) {
            return new CommonResult("200", "请求成功", list);
        } else {
            return new CommonResult("404", "请求失败", null);
        }
    }

    /*修改设备属性开关量定义*/
    @RequestMapping(value = "/Alarm/UpdDevicePropertyValueDefine", method = RequestMethod.POST)
    public CommonResult UpdDevicePropertyValueDefine(@RequestBody DevicePropertyValueDefine DevicePropertyValueDefine) {
        int i = AlarmServer.UpdDevicePropertyValueDefine(DevicePropertyValueDefine);
        if (i > 0) {
            return new CommonResult("200", "请求成功", null);
        } else {
            return new CommonResult("404", "请求失败", null);
        }
    }

    /*添加设备属性开关量定义*/
    @RequestMapping(value = "/Alarm/AddDevicePropertyValueDefine", method = RequestMethod.POST)
    public CommonResult AddDevicePropertyValueDefine(@RequestBody DevicePropertyValueDefine DevicePropertyValueDefine) {
        int i = AlarmServer.AddDevicePropertyValueDefine(DevicePropertyValueDefine);
        if (i > 0) {
            return new CommonResult("200", "请求成功", null);
        } else {
            return new CommonResult("404", "请求失败", null);
        }
    }

    /*删除设备属性开关量定义*/
    @RequestMapping(value = "/Alarm/DeleteDevicePropertyValueDefine", method = RequestMethod.POST)
    public CommonResult DeleteDevicePropertyValueDefine(@RequestBody DevicePropertyValueDefine DevicePropertyValueDefine) {
        int i = AlarmServer.DeleteDevicePropertyValueDefine(DevicePropertyValueDefine.getId());
        if (i > 0) {
            return new CommonResult("200", "请求成功", null);
        } else {
            return new CommonResult("404", "请求失败", null);
        }
    }

    /*查询报警状态*/
    @RequestMapping(value = "/Alarm/getalarmstate")
    public CommonResult getalarmstate() {
        List<alarmstate> list = AlarmServer.getalarmstate();
        if (list.size() > 0) {
            return new CommonResult("200", "请求成功", list);
        } else {
            return new CommonResult("404", "请求失败", null);
        }
    }

    /*获取报警记录*/
    @RequestMapping(value = "/Alarm/gettbalarm", method = RequestMethod.POST)
    public CommonResult gettbalarm(@RequestBody Tbalarm paging) {
        StringBuilder where = new StringBuilder();
        where.append("1=1");
        if (paging.getDevicePropertyId() != 0) {
            where.append(" and a.devicePropertyId='");
            where.append(paging.getDevicePropertyId() + "'");
        }
        if (paging.getAlarmDefineId() != 0) {
            where.append(" and a.alarmDefineId='");
            where.append(paging.getAlarmDefineId() + "'");
        }
        if (paging.getAlarmStateId() != 0) {
            where.append(" and a.alarmStateId='");
            where.append(paging.getAlarmStateId() + "'");
        }
        if (String.valueOf(paging.getAlarmTime()) != null && String.valueOf(paging.getAlarmTime()) != "") {
            String[] tim = String.valueOf(paging.getAlarmTime()).split("~");
            where.append(" and a.alarmTime<='");
            where.append(tim[1] + "'");
            where.append(" and a.alarmTime>='");
            where.append(tim[0] + "'");

        }
        tbalarms list = AlarmServer.gettbalarm(Integer.valueOf(paging.getIndex()), Integer.valueOf(paging.getPageSize()), where.toString());
        if (list.tbalarmlist.size() > 0) {
            return new CommonResult("200", "请求成功", list);
        } else {
            return new CommonResult("404", "请求失败", null);
        }
    }

    /*获取历史报警记录*/
    @RequestMapping(value = "/Alarm/gethistorytbalarm", method = RequestMethod.POST)
    public CommonResult gethistorytbalarm(@RequestBody Paging paging) {
        tbalarms list = AlarmServer.gethistorytbalarm(Integer.valueOf(paging.getIndex()), Integer.valueOf(paging.getPageSize()));
        if (list.tbalarmlist.size() > 0) {
            return new CommonResult("200", "请求成功", list);
        } else {
            return new CommonResult("404", "请求失败", null);
        }
    }

    /*处理报警*/
    @RequestMapping(value = "/Alarm/Uptbalarm", method = RequestMethod.POST)
    public CommonResult Uptbalarm(@RequestBody Tbalarm[] tb) {
        Date dNow = new Date();
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

        List<Tbalarm> roleArray = Arrays.asList(tb);
        int count = 0;
        for (int i = 0; i < roleArray.size(); i++) {
            roleArray.get(i).setAlarmStateId(1);
            roleArray.get(i).dealTime = ft.format(dNow);
            int j = AlarmServer.Uptbalarm(roleArray.get(i));
            count = count + j;
        }

        if (count == roleArray.size()) {
            return new CommonResult("200", "请求成功", null);
        } else {
            return new CommonResult("404", "请求失败", null);
        }
    }

    /*故障定义参数*/
    @RequestMapping(value = "/Alarm/GetAlarmDefineparameter")
    public CommonResult GetAlarmDefineparameter() {
        AlarmDefineParameter GetAlarmDefineparameter = new AlarmDefineParameter();
        GetAlarmDefineparameter = AlarmServer.GetAlarmDefineparameter();
        GetAlarmDefineparameter.setDevicePropertytree(AlarmServer.gettree());
        if (GetAlarmDefineparameter == null) {
            return new CommonResult("404", "请求失败", null);
        } else {
            return new CommonResult("200", "请求成功", GetAlarmDefineparameter);
        }
    }

    /*获取所有的报警消息
     * 韩熙
     * */

    @PostMapping("/alarm/getAllAlarmData")
    public CommonResult getAlarmData(@RequestBody AlarmData alarmData) throws ParseException {

        List<AlarmData> alarmDatas = tbAlarmService.getAlarmData();
        alarmDataService.insert(alarmDatas);

        CommonResult commonResult = new CommonResult();
        Integer totalCount = alarmDataService.getTotal(alarmData);
        List<AlarmData> alarmDataList = alarmDataService.getAll(alarmData);
        if (alarmDataList.size() > 0 && totalCount >= 0) {
            commonResult.setCode("200");
            commonResult.setMessage("请求成功");
            commonResult.setTotalCount(totalCount);
            commonResult.setData(alarmDataList);

        } else {
            commonResult.setCode("404");
            commonResult.setMessage("请求失败");
            return commonResult;
        }
        return commonResult;

    }

    /*获取所有报警等级
     * 韩熙
     * */
    @GetMapping("/alarm/toAlarmData")
    public List<grade> toAlarmData() {
        return AlarmServer.getgrade();
    }

    /*使用POI导出报警记录
     * 韩熙
     * */

    @GetMapping("/alarm/exportRecords")
    public String exportRecords(HttpServletResponse res) {

        List<AlarmData> alarmDataList = alarmDataService.getAllWithoutCondition();
        RecordExportUtil recordExportUtil = new RecordExportUtil();
        XSSFWorkbook wb = recordExportUtil.recordExport(alarmDataList);
        ByteArrayOutputStream fos = null;
        byte[] retArr = null;

        try {
            fos = new ByteArrayOutputStream();
            wb.write(fos);
            retArr = fos.toByteArray();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }

        try {
            OutputStream os = res.getOutputStream();
            res.reset();
            res.setHeader("Content-Disposition", "attachment;filename=报警记录.xls");
            res.setContentType("application/octet-stream;charset=utf-8");
            if (retArr != null) {
                os.write(retArr);
            }
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "导出成功";
    }
}

