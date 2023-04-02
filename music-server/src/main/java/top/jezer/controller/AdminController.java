package top.jezer.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.jezer.common.ErrorResp;
import top.jezer.common.SuccessResp;
import top.jezer.controller.dto.AdminDTO;
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
    public Object loginStatus(@RequestBody AdminDTO adminDTO) {
        String username = adminDTO.getUsername();
        String password = adminDTO.getPassword();
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password))
            return new ErrorResp("用户名和密码不能为空");
        try{
            adminDTO = adminService.login(adminDTO);
            if (null != adminDTO) {
                return new SuccessResp("登录成功", adminDTO);
            } else {
                return new ErrorResp("用户名或密码错误");
            }
        } catch (Exception e){
            throw new SystemException();
        }
    }
}
