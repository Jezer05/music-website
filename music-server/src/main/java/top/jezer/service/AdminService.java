package top.jezer.service;

import org.springframework.transaction.annotation.Transactional;
import top.jezer.controller.dto.LoginDTO;

@Transactional
public interface AdminService {
    /**
     * 验证账号密码
     */
    LoginDTO login(LoginDTO loginDTO);
}
