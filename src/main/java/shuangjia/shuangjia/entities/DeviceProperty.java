package shuangjia.shuangjia.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
*@Author
*@Description 设备属性对应类 完整
 *
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Mapper
public class DeviceProperty implements Serializable {
    private int id;
    private String code;
    private String name;
    private int deviceId;
    private int dataTypeId;
    private int alarmTypeId;
    private int alarmValue;
    private float minValue;
    private float maxValue;
    private float alarmMin;
    private float alarmMax;
    private int orderId;
    private String monitorValue;
    private String monitorDate;
    private String dataUnit;
    private int isShow;
    public String shortName;


    private String remark;

    private List<Device> device;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Device> getDevice() {
        return device;
    }

    public void setDevice(List<Device> device) {
        this.device = device;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getDataTypeId() {
        return dataTypeId;
    }

    public void setDataTypeId(int dataTypeId) {
        this.dataTypeId = dataTypeId;
    }

    public int getAlarmTypeId() {
        return alarmTypeId;
    }

    public void setAlarmTypeId(int alarmTypeId) {
        this.alarmTypeId = alarmTypeId;
    }

    public int getAlarmValue() {
        return alarmValue;
    }

    public void setAlarmValue(int alarmValue) {
        this.alarmValue = alarmValue;
    }

    public float getMinValue() {
        return minValue;
    }

    public void setMinValue(float minValue) {
        this.minValue = minValue;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(float maxValue) {
        this.maxValue = maxValue;
    }

    public float getAlarmMin() {
        return alarmMin;
    }

    public void setAlarmMin(float alarmMin) {
        this.alarmMin = alarmMin;
    }

    public float getAlarmMax() {
        return alarmMax;
    }

    public void setAlarmMax(float alarmMax) {
        this.alarmMax = alarmMax;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getMonitorValue() {
        return monitorValue;
    }

    public void setMonitorValue(String monitorValue) {
        this.monitorValue = monitorValue;
    }

    public String getMonitorDate() {
        return monitorDate;
    }

    public void setMonitorDate(String monitorDate) {
        this.monitorDate = monitorDate;
    }

    public String getDataUnit() {
        return dataUnit;
    }

    public void setDataUnit(String dataUnit) {
        this.dataUnit = dataUnit;
    }

    public int getIsShow() {
        return isShow;
    }

    public void setIsShow(int isShow) {
        this.isShow = isShow;
    }
}
