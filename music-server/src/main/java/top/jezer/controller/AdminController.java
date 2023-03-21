package top.jezer.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import top.jezer.common.ErrorResp;
import top.jezer.common.SuccessResp;
import top.jezer.exception.SystemException;
import top.jezer.service.serviceImpl.AdminServiceImpl;

@RestController
public class AdminController {
    @Autowired
    private AdminServiceImpl adminService;

    /**
     *  登录验证
     */
    @PostMapping("/admin/login/status")
    public Object loginStatus(HttpServletRequest req, HttpSession session) {
        String name = req.getParameter("name");
        String password = req.getParameter("password");
        try{
            boolean res = adminService.veritypwd(name, password);
            if (res) {
                session.setAttribute("name", name);
                return new SuccessResp<ObjectUtils.Null>("登录成功").getMessage();
            } else {
                return new ErrorResp("用户名或密码错误").getMessage();
            }
        } catch (Exception e){
            throw new SystemException("系统繁忙，请稍后再试");
        }
    }


}
