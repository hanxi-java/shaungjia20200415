package shuangjia.shuangjia.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class addmodl {
    public String pipname ;
    public BigDecimal lng ;
    public BigDecimal lat;
    public String nodecode;
    public String pipecolor;


}
