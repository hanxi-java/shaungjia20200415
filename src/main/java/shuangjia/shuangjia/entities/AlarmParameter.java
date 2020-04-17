package shuangjia.shuangjia.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmParameter {
    public String devicepropertyid;
    public String alarmMax;
    public String alarmMin;
    public String maxValue;
    public String minValue;


}

