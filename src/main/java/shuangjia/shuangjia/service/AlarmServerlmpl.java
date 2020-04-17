package shuangjia.shuangjia.service;

import org.springframework.stereotype.Service;
import shuangjia.shuangjia.dao.AlarmDao;
import shuangjia.shuangjia.entities.*;
import shuangjia.shuangjia.util.Page;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class AlarmServerlmpl implements AlarmServer{

    @Resource
    private AlarmDao AlarmDao;

    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    Date date=null;
    @Override
    public String gettree() {
        StringBuilder sb=new StringBuilder();
        List<Aranch> aranchlist=AlarmDao.GetAranch();
        List<Depart> Departlist=AlarmDao.GetDepart();
       List<Device> Devicelist=AlarmDao.GetDevice();
       List<DeviceProperty> DevicePropertylist=AlarmDao.GetDeviceProperty();


        sb.append("[");
        for (int i = 0; i <aranchlist.size() ; i++) {
            if (aranchlist.get(i)!=null)
            {
                sb.append("{");
                sb.append("\"id\":"); sb.append(aranchlist.get(i).getId()); sb.append(",");
                sb.append("\"name\":\"");sb.append(aranchlist.get(i).getName()); sb.append("\",");
                sb.append("\"code\":\"");sb.append(aranchlist.get(i).getCode());sb.append("\"");
                sb.append(",\"children\":[");
                for (int k=0;k<Departlist.size();k++)
                {
                    if (Departlist.get(k).getBranchId()==aranchlist.get(i).getId())
                    {
                        for(int j=0;j<Devicelist.size();j++)
                        {
                            if (Devicelist.get(j).getDepartId()==Departlist.get(k).getId())
                            {
                                sb.append("{");
                                sb.append("\"id\":"); sb.append(Devicelist.get(j).getDeviceId()); sb.append(",");
                                sb.append("\"name\":\"");sb.append(Devicelist.get(j).getRemark()+Devicelist.get(j).getName()); sb.append("\",");
                                sb.append("\"code\":\"");sb.append(Devicelist.get(j).getCode());sb.append("\",");

                                sb.append("\"children\":[");

                                for (int t=0;t<DevicePropertylist.size();t++)
                                {
                                    if (Devicelist.get(j).getDeviceId()==DevicePropertylist.get(t).getDeviceId()){
                                        sb.append("{");
                                        sb.append("\"id\":"); sb.append(DevicePropertylist.get(t).getId()); sb.append(",");

                                        sb.append("\"name\":\"");sb.append(DevicePropertylist.get(t).getName()); sb.append("\",");
                                        sb.append("\"code\":\"");sb.append(DevicePropertylist.get(t).getCode());sb.append("\"");
                                        sb.append("}");
                                        sb.append(",");
                                    }
                                }
                                char endstr=sb.charAt(sb.length() - 1);
                                if (endstr==',') {
                                    sb.deleteCharAt(sb.length() - 1);
                                }

                                sb.append("]");
                                sb.append("}");
                                sb.append(",");
                            }
                        }
                        char endstr=sb.charAt(sb.length() - 1);
                        if (endstr==',') {
                            sb.deleteCharAt(sb.length() - 1);
                        }
                    }
                }
                sb.append("]");
                sb.append("},");
            }
        }
        char endstr=sb.charAt(sb.length() - 1);
        if (endstr==',') {
            sb.deleteCharAt(sb.length() - 1);
        }
        sb.append("]");

        return sb.toString();
    }

    @Override
    public List<DeviceProperty> GetDevicePropertybydeviceid(int deviceid) {
        return AlarmDao.GetDevicePropertybydeviceid(deviceid);
    }

    @Override
    public int Updatealarmvalue(Alarmdevicemodel mo) {
        return AlarmDao.Updatealarmvalue(mo);
    }

    @Override
    public int UpdateFaultType(FaultType fa) {
        return AlarmDao.UpdateFaultType(fa);
    }

    @Override
    public int AddFaultType(FaultType fa) {
        return AlarmDao.AddFaultType(fa);
    }

    @Override
    public int DelteFaultType(int id) {
        return AlarmDao.DelteFaultType(id);
    }

    @Override
    public List<FaultType> getFaultType( ) {

        List<FaultType> list=AlarmDao.getFaultType();


        return list;
    }

    @Override
    public List<AlarmType> getAlarmType() {
        return AlarmDao.getAlarmType();
    }

    @Override
    public List<grade> getgrade() {
        return AlarmDao.getgrade();
    }

    @Override
    public int Updategrade(grade mo) {
        return AlarmDao.Updategrade(mo);
    }

    @Override
    public int Addgrade(grade fa) {
        return AlarmDao.Addgrade(fa);
    }

    @Override
    public int Deltegrade(int id) {
        return AlarmDao.Deltegrade(id);
    }

    @Override
    public AlarmDefines getAlarmDefine( int index, int pageSize,String wheres) {
        int count= Page.calculateRowIndex( index, pageSize);
        AlarmDefines list=new AlarmDefines();
        List<AlarmDefine> idlist=AlarmDao.gettreeid();
        List<DevicePropertyValueDefine> alist=AlarmDao.getDevicePropertyValueDefine();
         list.alarmDefineList=AlarmDao.getAlarmDefine(count,pageSize,wheres);
         if (list.alarmDefineList.size()>0)
         {
             for (int j=0;j<list.alarmDefineList.size();j++)
             {
                if (list.alarmDefineList.get(j).getIsStop()==0)
                {
                    list.alarmDefineList.get(j).setIsStopname("启用");
                }else {
                    list.alarmDefineList.get(j).setIsStopname("停用");
                }
                for (int k=0;k<idlist.size();k++)
                {
                    if (list.alarmDefineList.get(j).getDevicePropertyId()==idlist.get(k).getDevicePropertyId())
                    {
                        list.alarmDefineList.get(j).setDeviceid(idlist.get(k).getDeviceid());
                        list.alarmDefineList.get(j).setBranchid(idlist.get(k).getBranchid());
                        break;
                    }
                }
                 for (int t=0;t<alist.size();t++)
                 {
                     if (list.alarmDefineList.get(j).getDevicePropertyId()==alist.get(t).getPropertyId())
                     {
                         list.alarmDefineList.get(j).setPropertyDefine(alist.get(t).getPropertyDefine());
                     }
                 }

             }
         }

        int  i= list.alarmDefineList.size();//查询故障定义总数;

//        BigDecimal b=new BigDecimal(String.valueOf(AlarmDao. getAlarmDefinecount()));
//       int c =b.intValue();
        list.listcount=i;
        return list;
    }

    @Override
    public AlarmDefines getAlarmDefineByWhere(int index, int pageSize,String wheres) {
        int count= Page.calculateRowIndex( index, pageSize);
        AlarmDefines list=new AlarmDefines();
        list.alarmDefineList=AlarmDao.getAlarmDefineByWhere(count,pageSize,wheres);
        int  i= AlarmDao. getAlarmDefineByWherecount(wheres);//查询故障定义总数;

        list.listcount=i;
        return list;
    }

    @Override
    public int UpdAlarmDefine(AlarmDefine mo) {
        return AlarmDao.UpdAlarmDefine(mo);
    }

    @Override
    public int AddAlarmDefine(AlarmDefine fa) {
        return AlarmDao.AddAlarmDefine(fa);
    }

    @Override
    public int DeleteAlarmDefine(int id) {
        return AlarmDao.DeleteAlarmDefine(id);
    }

    @Override
    public List<DevicePropertyValueDefine> getDevicePropertyValueDefine() {
        return AlarmDao.getDevicePropertyValueDefine();
    }

    @Override
    public List<DevicePropertyValueDefine> getDevicePropertyValueDefinebydeviceid(int deviceid) {
        return AlarmDao.getDevicePropertyValueDefinebydeviceid(deviceid);
    }

    @Override
    public int UpdDevicePropertyValueDefine(DevicePropertyValueDefine mo) {
        return AlarmDao.UpdDevicePropertyValueDefine(mo);
    }

    @Override
    public int AddDevicePropertyValueDefine(DevicePropertyValueDefine fa) {
        return AlarmDao.AddDevicePropertyValueDefine(fa);
    }

    @Override
    public int DeleteDevicePropertyValueDefine(int id) {
        return AlarmDao.DeleteDevicePropertyValueDefine(id);
    }

    @Override
    public List<alarmstate> getalarmstate() {

        return AlarmDao.getalarmstate();
    }

    @Override
    public tbalarms gettbalarm(int index, int pageSize,String wheres) {
        int count= Page.calculateRowIndex( index, pageSize);
        tbalarms list=new tbalarms();
        list.tbalarmlist=AlarmDao.gettbalarm(count,pageSize,wheres);
        List<User> userlists=AlarmDao.getuser();
        for (int i=0;i<list.tbalarmlist.size();i++)
        {
            if (Integer.valueOf(list.tbalarmlist.get(i).getDealPersonId())!=0)
            {
                for (int k=0;k<userlists.size();k++)
                {
                    list.tbalarmlist.get(i).setDealPerson(userlists.get(k).getName());
                }
            }
        }
        list.counts=list.tbalarmlist.size();
        return list;
    }

    @Override
    public int Uptbalarm(Tbalarm mo) {
        return AlarmDao.Uptbalarm(mo);
    }

    @Override
    public tbalarms gethistorytbalarm(int pageIndexs, int pageSize) {
        int count= Page.calculateRowIndex( pageIndexs, pageSize);
        tbalarms list=new tbalarms();
        list.tbalarmlist=AlarmDao.gethistorytbalarm(count,pageSize);
        list.counts=AlarmDao.gethistorytbalarmcount();
        return list;
    }

    @Override
    public AlarmDefineParameter GetAlarmDefineparameter() {
        AlarmDefineParameter AlarmDefineParameters=new AlarmDefineParameter();
        //AlarmDefineParameters.setDevicePropertys(AlarmDao.GetDeviceProperty());
        AlarmDefineParameters.setFaultTypes(AlarmDao.getFaultType());
        AlarmDefineParameters.setAlarmTypes(AlarmDao.getAlarmType());
        AlarmDefineParameters.setGrades(AlarmDao.getgrade());
        AlarmDefineParameters.setDevicePropertyValueDefine(AlarmDao.getDevicePropertyValueDefine());
        return AlarmDefineParameters;
    }

    @Override
    public List<AlarmDefine> getAllAlarmDefine() {
        return AlarmDao.getAllAlarmDefine();
    }
    /*自动报警*/
    Map map = new HashMap<Integer, Integer>();
    Tbalarm doTbAlarm=new Tbalarm();
    @Override
    public void autoAlarm(AlarmDefine alarmDefine,String alarmTime,String value) throws ParseException {
        String alarmContent;
        int alarmStateId;
        String key=String.valueOf(alarmDefine.getId());
        /*先对比报警类型*/
        if(alarmDefine.getAlarmTypeId()==1){
            alarmContent="设备属性ID:"+alarmDefine.getDevicePropertyId()+":数值超上限";
            /*超上限*/
            if(Float.valueOf(value)>alarmDefine.getVal()){//数值超出触发报警
                /*确定是否有触发报警*/
                if(map.get(key)!=null){
                        int num=(int) map.get(key);
                      if(num>alarmDefine.getTriggerNum()){/*大于触发次数，产生报警记录*/
                          /*对比上次报警记录时间，超过1小时时才写入数据库*/
                          Tbalarm tbalarm=AlarmDao.getAlarmByStateIdTime(alarmDefine.getId(),alarmDefine.getDevicePropertyId());
                          if(tbalarm!=null && System.currentTimeMillis()-df.parse(tbalarm.getAlarmTime()).getTime()<3600000) {

                          }else {
                              doTbAlarm.setAlarmContent(alarmContent);
                              doTbAlarm.setAlarmDefineId(alarmDefine.getId());
                              doTbAlarm.setAlarmStateId(1);
                              doTbAlarm.setAlarmTime(alarmTime);
                              doTbAlarm.setDevicePropertyId(alarmDefine.getDevicePropertyId());
                              doTbAlarm.setAlarmValue(value);
                              AlarmDao.insertAlarm(doTbAlarm);
                          }
                          //移除
                          map.remove(key);
                      }else {//小于则次数+1
                              map.put(key, num + 1);
                      }
                }else {
                    if(alarmDefine.getTriggerNum()>0){
                        map.put(key,2);
                    }else{
                        Tbalarm tbalarm=AlarmDao.getAlarmByStateIdTime(alarmDefine.getId(),alarmDefine.getDevicePropertyId());
                        if(tbalarm!=null && System.currentTimeMillis()-df.parse(tbalarm.getAlarmTime()).getTime()<3600000){
                        }else {
                            /*添加报警记录*/
                            doTbAlarm.setAlarmContent(alarmContent);
                            doTbAlarm.setAlarmDefineId(alarmDefine.getId());
                            doTbAlarm.setAlarmStateId(1);
                            doTbAlarm.setAlarmTime(alarmTime);
                            doTbAlarm.setDevicePropertyId(alarmDefine.getDevicePropertyId());
                            doTbAlarm.setAlarmValue(value);
                            AlarmDao.insertAlarm(doTbAlarm);
                        }
                    }
                }
             }
            else{//数值正常，查询是否有未处理报警记录，若有则修改为已处理
                List<Tbalarm> tbalarms=AlarmDao.getAlarmByStateId(alarmDefine.getId(),alarmDefine.getDevicePropertyId());
                if(tbalarms.size()>0){
                    for (Tbalarm tbalarm:tbalarms){
                        tbalarm.setAlarmStateId(2);
                        tbalarm.setDealContent("自动处理");
                        tbalarm.setDealPersonId(0);
                        tbalarm.setDealTime(df.format(new Date()));
                        AlarmDao.updateAlarmState(tbalarm);
                    }
                }
            }
        } else if (alarmDefine.getAlarmTypeId()==2){
            alarmContent="设备属性ID:"+alarmDefine.getDevicePropertyId()+":数值超下限";
            /*超下限*/
            if(Float.valueOf(value)<alarmDefine.getVal()){
                /*确定是否有触发报警*/
                if(map.get(key)!=null){
                    int num=(int) map.get(key);
                    if(num>alarmDefine.getTriggerNum()){/*大于触发次数，产生报警记录*/
                        Tbalarm tbalarm=AlarmDao.getAlarmByStateIdTime(alarmDefine.getId(),alarmDefine.getDevicePropertyId());
                        if(tbalarm!=null && System.currentTimeMillis()-df.parse(tbalarm.getAlarmTime()).getTime()<3600000) {
                        }else {
                            /*添加报警记录*/
                            doTbAlarm.setAlarmContent(alarmContent);
                            doTbAlarm.setAlarmDefineId(alarmDefine.getId());
                            doTbAlarm.setAlarmStateId(1);
                            doTbAlarm.setAlarmTime(alarmTime);
                            doTbAlarm.setDevicePropertyId(alarmDefine.getDevicePropertyId());
                            doTbAlarm.setAlarmValue(value);
                            AlarmDao.insertAlarm(doTbAlarm);
                        }
                        //移除
                        map.remove(key);
                    }else {//小于则次数+1
                            map.put(key, num + 1);
                    }
                }else {
                    if(alarmDefine.getTriggerNum()>0){
                    map.put(key,2);
                    }else{
                        Tbalarm tbalarm=AlarmDao.getAlarmByStateIdTime(alarmDefine.getId(),alarmDefine.getDevicePropertyId());
                        if(tbalarm!=null && System.currentTimeMillis()-df.parse(tbalarm.getAlarmTime()).getTime()<3600000){
                        }else {
                            doTbAlarm.setAlarmContent(alarmContent);
                            doTbAlarm.setAlarmDefineId(alarmDefine.getId());
                            doTbAlarm.setAlarmStateId(1);
                            doTbAlarm.setAlarmTime(alarmTime);
                            doTbAlarm.setDevicePropertyId(alarmDefine.getDevicePropertyId());
                            doTbAlarm.setAlarmValue(value);
                            AlarmDao.insertAlarm(doTbAlarm);
                        }
                    }
                }
             }else {
                //数值正常，查询是否有未处理报警记录，若有则修改为处理
                List<Tbalarm> tbalarms=AlarmDao.getAlarmByStateId(alarmDefine.getId(),alarmDefine.getDevicePropertyId());
                if(tbalarms.size()>0){
                    for (Tbalarm tbalarm:tbalarms){
                        tbalarm.setAlarmStateId(2);
                        tbalarm.setDealContent("自动处理");
                        tbalarm.setDealPersonId(0);
                        tbalarm.setDealTime(df.format(new Date()));
                        AlarmDao.updateAlarmState(tbalarm);
                    }
                }
            }
        }
    }
}

