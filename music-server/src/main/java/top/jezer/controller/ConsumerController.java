package top.jezer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.jezer.common.ErrorResp;
import top.jezer.common.SuccessResp;
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
