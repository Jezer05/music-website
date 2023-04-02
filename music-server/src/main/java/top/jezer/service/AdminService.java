package top.jezer.service;

import org.springframework.transaction.annotation.Transactional;
import top.jezer.controller.dto.AdminDTO;

@Transactional
public interface AdminService {
    /**
     * 验证账号密码
     */
    AdminDTO login(AdminDTO adminDTO);
}
