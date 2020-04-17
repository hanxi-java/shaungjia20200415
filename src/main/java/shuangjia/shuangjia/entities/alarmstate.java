package shuangjia.shuangjia.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Mapper
public class alarmstate {
    public int id;
    public String code;
    public String name;
}

