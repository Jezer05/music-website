package top.jezer.exception;

public class SystemException extends RuntimeException{
    private String msg;
    public SystemException(String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }
}
