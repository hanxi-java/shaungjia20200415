package shuangjia.shuangjia.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Mapper
public class FaultType {
    public int id;
    public String code;
    public String name;
    public String faultDesc;//故障描述
    public String alarmTypename;//父级故障
    public int alarmTypid;//父级id



}
