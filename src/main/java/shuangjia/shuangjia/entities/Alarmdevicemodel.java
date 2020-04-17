package shuangjia.shuangjia.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Alarmdevicemodel {
    public int devicepropertyid;
    public float minValue;
    public float maxValue;
    public float alarmMin;
    public float alarmMax;


    public int getDevicepropertyid() {
        return devicepropertyid;
    }

    public void setDevicepropertyid(int devicepropertyid) {
        this.devicepropertyid = devicepropertyid;
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
}
