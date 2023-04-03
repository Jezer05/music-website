package top.jezer.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.jezer.constant.Code;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExpiredResp {
    private Integer code;
    private boolean isSuccess;
    private String type;
    private String message;
    private Object data;

    public ExpiredResp(String message) {
        this.code = Code.EXPIRED;
        this.isSuccess = false;
        this.type = "error";
        this.message = message;
        this.data = null;
    }
}