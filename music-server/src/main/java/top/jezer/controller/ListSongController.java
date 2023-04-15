package top.jezer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import top.jezer.common.ErrorResp;
import top.jezer.common.SuccessResp;
import top.jezer.controller.dto.ListSongDTO;
import top.jezer.domain.ListSong;
import top.jezer.domain.Song;
import top.jezer.exception.SystemException;
import top.jezer.service.serviceImpl.ListSongServiceImpl;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/listSongs")
public class ListSongController {
    @Autowired
    private ListSongServiceImpl listSongService;
    //<editor-fold desc="增">
    @PostMapping("/{songListId}")
    private Object addSongsIntoList(@RequestBody List<Integer> songIds, @PathVariable Integer songListId){
        List<ListSong> listSongs = new ArrayList<>();
        for (Integer songId : songIds){
            ListSong tmp = new ListSong();
            tmp.setSongId(songId);
            tmp.setSongListId(songListId);
            listSongs.add(tmp);
        }
        try {
            boolean res = listSongService.addSongsIntoList(listSongs);
            if (res) return new SuccessResp("添加成功");
            else return new ErrorResp("添加失败");
        }catch (Exception e){
            throw new SystemException();
        }
    }
    //</editor-fold>

    //<editor-fold desc="删">
    @DeleteMapping("/{id}")
    public Object deleteListSong(@PathVariable Integer id){
        try {
           boolean res = listSongService.deleteListSong(id);
           if (res) return new SuccessResp("删除成功");
           else  return new ErrorResp("未找到对应内容");
        }catch (Exception e){
            throw new SystemException();
        }
    }
    @DeleteMapping
    public Object deleteListSongs(@RequestBody List<Integer> ids){
        try {
            boolean res = listSongService.deleteListSongs(ids);
            if (res) return new SuccessResp("删除成功");
            else  return new ErrorResp("未找到对应内容");
        }catch (Exception e){
            throw new SystemException();
        }
    }

    //</editor-fold>

    //<editor-fold desc="改">

    //</editor-fold>

    //<editor-fold desc="查">
    // 根据歌单id查找对应歌曲
    @GetMapping("/songList/{id}")
    public Object getListSongByListId(@PathVariable("id") Integer songListId){
        try {
            List<ListSongDTO> res = listSongService.getSongListByListId(songListId);
            if (res != null) return new SuccessResp("查询成功", res);
            else return new ErrorResp("未找到对应内容");
        }catch (Exception e){
            throw new SystemException();
        }
    }
    //</editor-fold>
}
