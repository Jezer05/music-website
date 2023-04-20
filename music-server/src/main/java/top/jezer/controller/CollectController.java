package top.jezer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.jezer.common.ErrorResp;
import top.jezer.common.SuccessResp;
import top.jezer.controller.dto.CollectListDTO;
import top.jezer.controller.dto.CollectSongDTO;
import top.jezer.domain.Collect;
import top.jezer.exception.SystemException;
import top.jezer.service.serviceImpl.CollectServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/collects")
public class CollectController {
    @Autowired
    private CollectServiceImpl collectService;
    //<editor-fold desc="增">
    // 收藏歌曲
    @PostMapping("/song/{id}")
    public Object addCollectSong(@PathVariable Integer id, @RequestBody Collect collect){
        collect.setUserId(id);
        collect.setType(0);
        try {
           boolean res = collectService.addCollectSong(collect);
           if (res) return new SuccessResp("歌曲收藏成功");
           else return new ErrorResp("歌曲收藏失败");
        }catch (Exception e){
            throw new SystemException();
        }
    }
    //</editor-fold>

    //<editor-fold desc="删">
    @DeleteMapping("{id}")
    public Object deleteCollect(@PathVariable Integer id){
        try {
           boolean res = collectService.deleteCollect(id);
           if (res)
               return new SuccessResp("删除成功过");
           else return new ErrorResp("未找到对应内容");
        }catch (Exception e){
            throw new SystemException();
        }
    }
    @DeleteMapping()
    public Object deleteCollect(@RequestBody List<Integer> ids){
        try {
            boolean res = collectService.deleteCollects(ids);
            if (res)
                return new SuccessResp("删除成功过");
            else return new ErrorResp("未找到对应内容");
        }catch (Exception e){
            throw new SystemException();
        }
    }

    @DeleteMapping("/song/{id}")
    public Object deleteCollectSongBySongId(@PathVariable Integer id, @RequestParam Integer songId){
        try {
            boolean res = collectService.delCollectSongBySongId(id, songId);
            if (res)
                return new SuccessResp("取消收藏成功");
            else return new ErrorResp("未找到对应内容");
        }catch (Exception e){
            throw new SystemException();
        }
    }
    //</editor-fold>

    //<editor-fold desc="改">

    //</editor-fold>

    //<editor-fold desc="查">
    // 获得用户的收藏歌曲
    @GetMapping("/song/user/{id}")
    public Object getCollectSongByUserId(@PathVariable("id") Integer userId){
        try {
            List<CollectSongDTO> res = collectService.getCollectSongByUserId(userId);
            if (res != null)
                return new SuccessResp("查询成功", res);
            else return new ErrorResp("未收藏歌曲");
        }catch (Exception e){
            throw new SystemException();
        }
    }

    // 获得用户的收藏歌单
    @GetMapping("/list/user/{id}")
    public Object getCollectListByUserId(@PathVariable("id") Integer userId){
        try {
            List<CollectListDTO> res = collectService.getCollectListByUserId(userId);
            if (res != null)
                return new SuccessResp("查询成功", res);
            else return new ErrorResp("未收藏歌单");
        }catch (Exception e){
            throw new SystemException();
        }
    }
    //</editor-fold>
}
