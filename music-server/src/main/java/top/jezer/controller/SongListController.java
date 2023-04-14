package top.jezer.controller;

import jakarta.websocket.server.PathParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import top.jezer.common.ErrorResp;
import top.jezer.common.SuccessResp;
import top.jezer.domain.SongList;
import top.jezer.exception.SystemException;
import top.jezer.service.serviceImpl.SongListServiceImpl;
import top.jezer.utils.FileUtils;

import java.util.List;

@RestController
@RequestMapping("songLists")
public class SongListController {
    @Autowired
    private SongListServiceImpl songListService;
    //<editor-fold desc="增">
    @PostMapping
    public Object addSongList(@RequestBody SongList songList){
        String title = songList.getTitle().trim();
        try {
           if (null != songListService.getSongListByTitleEq(title))
               return new ErrorResp("已存在同名歌单");
           boolean res = songListService.addSongList(songList);
           if (res) return new SuccessResp("歌单创建成功");
           else return new ErrorResp("歌单创建失败");
        }catch (Exception e){
            throw new SystemException();
        }
    }
    //</editor-fold>

    //<editor-fold desc="删">
    @DeleteMapping("{id}")
    public Object deleteSongList(@PathVariable Integer id){
        try {
           boolean res = songListService.deleteSongList(id);
           if (res) return new SuccessResp("删除成功");
           else return new ErrorResp("未找到对应歌单");
        }catch (Exception e){
            throw new SystemException();
        }
    }
    @DeleteMapping
    public Object deleteSongLists(@RequestBody List<Integer> ids){
        try {
            boolean res = songListService.deleteSongLists(ids);
            if (res) return new SuccessResp("删除成功");
            else return new ErrorResp("未找到对应歌单");
        }catch (Exception e){
            throw new SystemException();
        }
    }
    //</editor-fold>

    //<editor-fold desc="改">
    @PutMapping("{id}")
    public Object updateSongListMsg(@PathVariable Integer id, @RequestBody SongList songList){
        songList.setId(id);
        try {
            boolean res = songListService.updateSongList(songList);
            if (res) return new SuccessResp("歌单更新成功");
            else  return new ErrorResp("未找到对应歌单");
        }catch (Exception e){
            throw new SystemException();
        }
    }
    @PutMapping("/img/{id}")
    public Object updateSongListImg(@PathVariable Integer id, @RequestParam MultipartFile file){
        String pic = FileUtils.uploadFile(file, 0, "/img/songListPic");
        SongList songList = new SongList();
        songList.setPic(pic);
        songList.setId(id);
        try {
           boolean res = songListService.updateSongList(songList);
           if (res) return new SuccessResp("图片更新成功");
           else return new ErrorResp("未找到对应歌单信息");
        }catch (Exception e){
            throw new SystemException();
        }
    }
    //</editor-fold>

    //<editor-fold desc="查">
    @GetMapping
    public Object getAllSongList(){
        try {
           List<SongList> res = songListService.getAllSongList();
           if (res != null)
               return new SuccessResp("查询成功", res);
           else return new ErrorResp("未找到对应内容");
        }catch (Exception e){
            throw new SystemException();
        }
    }
    //</editor-fold>
}
