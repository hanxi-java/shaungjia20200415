package shuangjia.shuangjia.entities;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;

//监控数据表
@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Mapper
public class Data {

    private int id;

    private  int device_property_id;

    private  String collect_value;

    private  String collect_date;

    private DeviceProperty deviceProperty;

    private Device device;

    private DataType dataType;



    public DataType getDataType() {
        return dataType;
    }

    public void setDataType(DataType dataType) {
        this.dataType = dataType;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public DeviceProperty getDeviceProperty() {
        return deviceProperty;
    }

    public void setDeviceProperty(DeviceProperty deviceProperty) {
        this.deviceProperty = deviceProperty;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDevice_property_id() {
        return device_property_id;
    }

    public void setDevice_property_id(int device_property_id) {
        this.device_property_id = device_property_id;
    }

    public String getCollect_value() {
        return collect_value;
    }

    public void setCollect_value(String collect_value) {
        this.collect_value = collect_value;
    }

    public String getCollect_date() {
        return collect_date;
    }

    public void setCollect_date(String collect_date) {
        this.collect_date = collect_date;
    }
}
