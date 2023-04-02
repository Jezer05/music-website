package top.jezer.common;

import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.jezer.constant.Code;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResp {
    private Integer code;
    private boolean isSuccess;
    private String type;
    private String message;
    private Object data;

    public ErrorResp(String message) {
        this.code = Code.ERROR;
        this.isSuccess = false;
        this.type = "error";
        this.message = message;
        this.data = null;
    }
}
