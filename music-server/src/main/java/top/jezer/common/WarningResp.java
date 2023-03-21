package top.jezer.common;

import com.alibaba.fastjson2.JSONObject;
import top.jezer.constant.Code;

public class WarningResp {
    private JSONObject resp = new JSONObject();

    public WarningResp(String message) {
        resp.put("code", Code.WARNING);
        resp.put("message", message);
        resp.put("success", false);
        resp.put("type", "warning");
        resp.put("data", null);
    }
    public JSONObject getMessage() {
        return resp;
    }
}
