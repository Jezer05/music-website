package top.jezer.common;

import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.jezer.constant.Code;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FatalResp {
    private Integer code;
    private boolean isSuccess;
    private String type;
    private String message;
    private Object data;

    public FatalResp(String message) {
        this.code = Code.FATAL;
        this.isSuccess = false;
        this.type = "error";
        this.message = message;
        this.data = null;
    }
}
