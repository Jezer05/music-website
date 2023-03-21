package top.jezer.service;

import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface AdminService {
    /**
     * 验证账号密码
     */
    boolean veritypwd(String name, String password);
}
