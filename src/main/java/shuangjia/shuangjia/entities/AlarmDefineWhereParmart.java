package shuangjia.shuangjia.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AlarmDefineWhereParmart {
    public String index;
    public String pageSize;
    public String name;
    public String alarmTypeId;//报警类型
    public String isStop;//是否停用
    public String gradeId;//等级
}
