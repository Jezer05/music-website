package top.jezer.common;

import com.alibaba.fastjson2.JSONObject;
import top.jezer.constant.Code;

public class FatalResp {
    private JSONObject resp = new JSONObject();

    public FatalResp(String message) {
        resp.put("code", Code.FATAL);
        resp.put("message", message);
        resp.put("success", false);
        resp.put("type", "fatal");
        resp.put("data", null);
    }
    public JSONObject getMessage() {
        return resp;
    }
}
