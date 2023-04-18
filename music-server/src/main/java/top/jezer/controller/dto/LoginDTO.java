package top.jezer.controller.dto;

import lombok.Data;

@Data
public class LoginDTO {
    private Integer id;
    private String username;
    private String password;
    private String avatar;
    private String token;
}
