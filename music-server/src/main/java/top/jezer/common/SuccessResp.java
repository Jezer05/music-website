package top.jezer.common;

import com.alibaba.fastjson2.JSONObject;
import top.jezer.constant.Code;

public class SuccessResp<T> {
    private JSONObject resp = new JSONObject();

    public SuccessResp(String message) {
        resp.put("code", Code.SUCCESS);
        resp.put("message", message);
        resp.put("success", true);
        resp.put("type", "success");
        resp.put("data", null);
    }

    public SuccessResp(String message, T data) {
        resp.put("code", Code.SUCCESS);
        resp.put("message", message);
        resp.put("success", true);
        resp.put("type", "success");
        resp.put("data", data);
    }

    public JSONObject getMessage() {
        return resp;
    }
}
