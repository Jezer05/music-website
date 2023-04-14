package top.jezer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.jezer.common.ErrorResp;
import top.jezer.common.SuccessResp;
import top.jezer.controller.dto.CommentDTO;
import top.jezer.exception.SystemException;
import top.jezer.service.serviceImpl.CommentServiceImpl;

import java.util.List;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentServiceImpl commentService;

    //<editor-fold desc="增">

    //</editor-fold>

    //<editor-fold desc="删">
    @DeleteMapping("/{id}")
    public Object deleteComment(@PathVariable Integer id){
        try {
            boolean res = commentService.deleteComment(id);
            if (res)
                return new SuccessResp("删除成功");
            else return new ErrorResp("未找到对应评论");
        }catch (Exception e){
            throw new SystemException();
        }
    }
    @DeleteMapping
    public Object deleteComments(@RequestBody List<Integer> ids){
        try {
           boolean res = commentService.deleteComments(ids);
           if (res)
               return new SuccessResp("删除成功");
           else return new ErrorResp("未找到对应评论");
        }catch (Exception e){
            throw new SystemException();
        }
    }
    //</editor-fold>

    //<editor-fold desc="改">

    //</editor-fold>

    //<editor-fold desc="查">
    // 根据歌曲Id返回评论
    @GetMapping("/song/detail/{id}")
    public Object getCommentBySongId(@PathVariable("id") Integer songId){
        try {
            List<CommentDTO> res = commentService.getCommentBySongId(songId);
            if (null != res)
                return new SuccessResp("查询成功", res);
            else return new ErrorResp("未找到相关评论");
        }catch (Exception e){
            throw new SystemException();
        }
    }
    // 根据歌单Id返回评论
    @GetMapping("/songList/detail/{id}")
    public Object getCommentBySongListId(@PathVariable("id") Integer songListId){
        try {
           List<CommentDTO> res = commentService.getCommentBySongListId(songListId);
           if (res != null) return new SuccessResp("查询成功",res);
           else return new ErrorResp("未找到对应评论");
        }catch (Exception e){
            throw new SystemException();
        }
    }
    //</editor-fold>
}
