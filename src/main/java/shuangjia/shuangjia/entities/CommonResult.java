package shuangjia.shuangjia.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
@NoArgsConstructor
public class CommonResult<T> {

    private String code;
    private String message;
    private Integer totalCount;
    private T data;

    public CommonResult(String code, String message,T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
