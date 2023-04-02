package top.jezer.common;

import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.jezer.constant.Code;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuccessResp {
    private Integer code;
    private boolean isSuccess;
    private String type;
    private String message;
    private Object data;

    public SuccessResp(String message) {
        this.code = Code.SUCCESS;
        this.isSuccess = true;
        this.type = "success";
        this.message = message;
        this.data = null;
    }
    public SuccessResp(String message, Object data) {
        this.code = Code.SUCCESS;
        this.isSuccess = true;
        this.type = "success";
        this.message = message;
        this.data = data;
    }
}
