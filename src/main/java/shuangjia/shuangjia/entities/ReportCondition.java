package shuangjia.shuangjia.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;
/*
* 定义接收前台传入报告类型的实体类，辅助日报/月报/年报的路由跳转
* */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Mapper
public class ReportCondition {
    private String reportType;
    private String stage;
    private Integer deviceId;
    private String reportHour;

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public Integer getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }

    public String getReportHour() {
        return reportHour;
    }

    public void setReportHour(String reportHour) {
        this.reportHour = reportHour;
    }
}
