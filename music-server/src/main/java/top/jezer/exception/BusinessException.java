package top.jezer.exception;

/**
 * 封装业务异常对象
 */
public class BusinessException extends RuntimeException{
    private String msg;
    public BusinessException(String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
