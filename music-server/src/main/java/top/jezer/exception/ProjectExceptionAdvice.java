package top.jezer.exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.jezer.common.ErrorResp;
import top.jezer.common.FatalResp;

//@RestControllerAdvice用于标识当前类为REST风格对应的异常处理器
@RestControllerAdvice
public class ProjectExceptionAdvice {
    //@ExceptionHandler用于设置当前处理器类对应的异常类型
    @ExceptionHandler(SystemException.class)
    public Object doSystemException(SystemException ex){
        //记录日志
        //发送消息给运维
        //发送邮件给开发人员,ex对象发送给开发人员
        System.out.println("系统异常："+ ex.getMessage());
        return new FatalResp(ex.getMsg()).getMessage();
    }

    @ExceptionHandler(BusinessException.class)
    public Object doBusinessException(BusinessException ex){
        System.out.println("业务异常："+ ex.getMessage());
        return new ErrorResp(ex.getMsg()).getMessage();
    }

    //除了自定义的异常处理器，保留对Exception类型的异常处理，用于处理非预期的异常
    @ExceptionHandler(Exception.class)
    public Object doOtherException(Exception ex){
        //记录日志
        //发送消息给运维
        //发送邮件给开发人员,ex对象发送给开发人员
        System.out.println(ex.getMessage());
        return new ErrorResp("未知错误，程序猿正在努力解决").getMessage();
    }
}
