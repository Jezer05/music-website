package top.jezer.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 封装业务异常对象
 */
@Data
@AllArgsConstructor
public class BusinessException extends RuntimeException{
    private String msg;
    public BusinessException(){
        this.msg = "系统繁忙，请稍后再试";
    }
}
