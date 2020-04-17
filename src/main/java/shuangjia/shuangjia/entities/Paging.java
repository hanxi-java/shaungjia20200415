package shuangjia.shuangjia.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;
/**
*@Author 张典
*@Description 页码实体类
 *
*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Paging {
    public String index;
    public String pageSize;
    public String wheres;
}

