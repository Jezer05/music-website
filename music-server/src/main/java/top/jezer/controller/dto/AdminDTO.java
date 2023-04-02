package top.jezer.controller.dto;

import lombok.Data;

@Data
public class AdminDTO {
    private Integer id;
    private String username;
    private String password;
    private String token;
}
