package top.jezer.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.jezer.common.ErrorResp;
import top.jezer.common.SuccessResp;
import top.jezer.controller.dto.LoginDTO;
import top.jezer.exception.SystemException;
import top.jezer.service.serviceImpl.AdminServiceImpl;

@RestController
@RequestMapping("/admins")
public class AdminController {
    @Autowired
    private AdminServiceImpl adminService;

    /**
     *  登录验证
     */
    @PostMapping("/login")
    public Object loginStatus(@RequestBody LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password))
            return new ErrorResp("用户名和密码不能为空");
        try{
            loginDTO = adminService.login(loginDTO);
            if (null != loginDTO) {
                return new SuccessResp("登录成功", loginDTO);
            } else {
                return new ErrorResp("用户名或密码错误");
            }
        } catch (Exception e){
            throw new SystemException();
        }
    }
}
