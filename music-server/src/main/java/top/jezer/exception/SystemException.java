package top.jezer.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
public class SystemException extends RuntimeException{
    private String msg;
    public SystemException(){
        this.msg = "系统异常，请稍后再试";
    }
}
