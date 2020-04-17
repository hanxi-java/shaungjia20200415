package shuangjia.shuangjia.entities;

import java.util.List;

public class Central {
    private  String deviceName;
    private List<TotalData> totalData;

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public List<TotalData> getTotalData() {
        return totalData;
    }

    public void setTotalData(List<TotalData> totalData) {
        this.totalData = totalData;
    }
}
