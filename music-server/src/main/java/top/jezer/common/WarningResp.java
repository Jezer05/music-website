package top.jezer.common;

import com.alibaba.fastjson2.JSONObject;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.jezer.constant.Code;

//public class WarningResp {
//    private JSONObject resp = new JSONObject();
//
//    public WarningResp(String message) {
//        resp.put("code", Code.WARNING);
//        resp.put("message", message);
//        resp.put("isSuccess", false);
//        resp.put("type", "warning");
//        resp.put("data", null);
//    }
//    public JSONObject getMessage() {
//        return resp;
//    }
//}

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WarningResp {
    private Integer code;
    private boolean isSuccess;
    private String type;
    private String message;
    private Object data;

    public WarningResp(String message) {
        this.code = Code.WARNING;
        this.isSuccess = false;
        this.type = "warning";
        this.message = message;
        this.data = null;
    }
}