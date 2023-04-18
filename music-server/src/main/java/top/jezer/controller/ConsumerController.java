package top.jezer.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.jezer.common.ErrorResp;
import top.jezer.common.SuccessResp;
import top.jezer.controller.dto.LoginDTO;
import top.jezer.domain.Consumer;
import top.jezer.exception.SystemException;
import top.jezer.service.serviceImpl.ConsumerServiceImpl;

import java.util.List;

@RestController
@RequestMapping("consumers")
public class ConsumerController {
    @Autowired
    private ConsumerServiceImpl consumerService;
    //<editor-fold desc="增">

    //</editor-fold>

    //<editor-fold desc="删">
    @DeleteMapping("/{id}")
    public Object deleteConsumer(@PathVariable Integer id){
        try {
           boolean res = consumerService.deleteConsumer(id);
           if (res)
               return new SuccessResp("用户删除成功");
           else return new ErrorResp("未找到对应用户");
        }catch (Exception e){
            throw new SystemException();
        }
    }

    @DeleteMapping
    public Object deleteConsumers(@RequestBody List<Integer> ids){
        try {
            boolean res = consumerService.deleteConsumers(ids);
           if (res)
               return new SuccessResp("用户删除成功");
            else return new ErrorResp("未找到对应用户");
        }catch (Exception e){
            throw new SystemException();
        }
    }

    //</editor-fold>

    //<editor-fold desc="改">
    @PostMapping("/login")
    public Object loginStatus(@RequestBody LoginDTO loginDTO) {
        loginDTO.setUsername(loginDTO.getUsername().trim());
        try{
            loginDTO = consumerService.login(loginDTO);
            if (null != loginDTO) {
                return new SuccessResp("登录成功", loginDTO);
            } else {
                return new ErrorResp("用户名或密码错误");
            }
        } catch (Exception e){
            throw new SystemException();
        }
    }
    @PostMapping("/register")
    public Object register(@RequestBody LoginDTO loginDTO) {
        String name = loginDTO.getUsername().trim();
        try{
            if (null != consumerService.getConsumerByNameEq(name))
                return new ErrorResp("已存在同名用户");
            Consumer consumer = new Consumer();
            consumer.setUsername(name);
            consumer.setPassword(loginDTO.getPassword().trim());
            consumer.setAvatar("/img/avatarImages/user.jpg");
            consumer.setSex(3);
            boolean res = consumerService.addConsumer(consumer);
            if (res) return new SuccessResp("注册成功", consumer);
            else return new ErrorResp("注册失败");
        } catch (Exception e){
            throw new SystemException();
        }
    }
    //</editor-fold>

    //<editor-fold desc="查">
    @GetMapping
    public Object getAllConsumer(){
        try {
           return new SuccessResp("查询成功", consumerService.getAllConsumer());
        }catch (Exception e){
            throw new SystemException();
        }
    }
    //</editor-fold>
}
