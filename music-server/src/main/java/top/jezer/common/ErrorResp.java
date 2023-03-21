package top.jezer.common;

import com.alibaba.fastjson2.JSONObject;
import top.jezer.constant.Code;

public class ErrorResp {
    private JSONObject resp = new JSONObject();

    public ErrorResp(String message) {
        resp.put("code", Code.ERROR);
        resp.put("message", message);
        resp.put("success", false);
        resp.put("type", "error");
        resp.put("data", null);
    }
    public JSONObject getMessage() {
        return resp;
    }
}
