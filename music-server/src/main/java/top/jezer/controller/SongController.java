package top.jezer.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.jezer.common.ErrorResp;
import top.jezer.common.SuccessResp;
import top.jezer.constant.ResourceLocation;
import top.jezer.domain.Song;
import top.jezer.exception.SystemException;
import top.jezer.service.SongService;
import top.jezer.service.serviceImpl.SongServiceImpl;
import top.jezer.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/songs")
public class SongController {
    @Autowired
    private SongServiceImpl songService;

    //<editor-fold desc="增">
    // 添加新歌曲
    @PostMapping
    public Object addSong(Song song, @RequestParam("file") MultipartFile file) {
        String name = song.getName().trim();
        try {
            if (songService.getSongByNameEq(name) != null)
                return new ErrorResp("已存在同名歌曲");
        }catch (Exception e){
            throw new SystemException();
        }
        // 创建音乐资源路径
        String storeUrlPath = FileUtils.uploadFile(file, 0, "/song");
        // 设置默认歌曲图标
        song.setPic("/img/songPic/tubiao.jpg");
        song.setUrl(storeUrlPath);
        try {
            boolean res = songService.addSong(song);
            if (res) {
                return new SuccessResp("歌曲添加成功");
            } else {
                return new ErrorResp("歌曲添加失败");
            }
        } catch (Exception e) {
            throw new SystemException();
        }
    }
    //</editor-fold>

    //<editor-fold desc="删">
    // 删除歌曲
    @DeleteMapping("/{id}")
    public Object deleteSong(@PathVariable("id") Integer id){
        try{
            boolean res = songService.deleteSong(id);
            if (res) {
                return new SuccessResp("歌曲删除成功");
            } else {
                return new ErrorResp("未找到对应信息，请稍后再试");
            }
        }catch(Exception e){
            throw new SystemException();
        }
    }
    @DeleteMapping
    public Object deleteSingers(@RequestBody List<Integer> ids){
        try {
            boolean res = songService.deleteSongs(ids);
            if (res)
                return new SuccessResp("歌手删除成功");
            else return new ErrorResp("未找到对应歌手信息，请刷新后再试");
        }catch (Exception e){
            throw new SystemException();
        }
    }
    //</editor-fold>

    //<editor-fold desc="改">
    // 更新歌曲信息
    @PutMapping("/{id}")
    public Object updateSongMsg(@RequestBody Song song, @PathVariable Integer id) {
        song.setId(id);
        try {
            boolean res = songService.updateSong(song);
            if (res) {
                return new SuccessResp("歌曲信息修改成功");
            } else {
                return new ErrorResp("未找到对应歌曲信息，请刷新后再试");
            }
        }catch (Exception e){
            throw new SystemException();
        }
    }
    // 更新歌曲图片
    @PutMapping("/img/{id}")
    public Object updateSongPic(@RequestParam("file") MultipartFile file, @PathVariable("id") Integer id) {
        // TODO 删除旧图片
        String storeUrlPath = FileUtils.uploadFile(file, 1, "/img/songPic");
        Song song = new Song();
        song.setId(id);
        song.setPic(storeUrlPath);
        try {
            boolean res = songService.updateSong(song);
            // TODO 图片上传成功但是数据库失败，删除不必要资源
            if (res) {
                return new SuccessResp("歌曲图片上传成功", storeUrlPath);
            } else {
                return new ErrorResp("未找到对应歌曲信息");
            }
        } catch (Exception e) {
            return new SystemException();
        }
    }

    // 更新歌曲资源
    @PutMapping("/url/{id}")
    public Object updateSongUrl(@RequestParam("file") MultipartFile file, @PathVariable("id") Integer id) {
        // TODO 删除旧资源 先检查，再上传
        String storeUrlPath = FileUtils.uploadFile(file, 0, "/song");
        Song song = new Song();
        song.setId(id);
        song.setUrl(storeUrlPath);
        try {
            // TODO 图片上传成功但是数据库失败，删除不必要资源
            boolean res = songService.updateSong(song);
            if (res) {
                return new SuccessResp("上传成功", storeUrlPath);
            } else {
                return new ErrorResp("未找到对应歌曲");
            }
        } catch (Exception e) {
            return new SystemException();
        }
    }
    //</editor-fold>

    //<editor-fold desc="查">
    // 根据歌手Id查询歌曲
    @GetMapping("/singer/detail/{id}")
    public Object getSongBySingerId(@PathVariable("id") Integer singerId){
        try{
            List<Song> res = songService.getSongBySingerId(singerId);
            if (null != res)
                return new SuccessResp("歌曲查询成功",res);
            else return new ErrorResp("未找到对应歌手的歌曲信息");
        }catch (Exception e){
            throw new SystemException();
        }
    }

    // 查询所有歌曲
    @GetMapping
    public Object getAllSong(){
        try{
            return new SuccessResp("歌曲查询成功", songService.getAllSong());
        }catch (Exception e){
            throw new SystemException();
        }
    }
    //</editor-fold>

    //<editor-fold desc="暂未使用">
    // 返回指定歌曲ID的歌曲
    @GetMapping("/{id}")
    public Object getSongById(@PathVariable("id") Integer id){
        try{
            Song res = songService.getSongById(id);
            if (null != res)
                return new SuccessResp("查询成功", res);
            else return new ErrorResp("未找到对应歌曲信息");
        }catch (Exception e){
            throw new SystemException();
        }
    }
    // 根据歌手查询歌曲
    @GetMapping("/singerName/detail")
    public Object getSongByName(String name){
        if (null == name)
            return new ErrorResp("歌曲名称不能为空").getMessage();
        try{
            return new SuccessResp("歌曲查询成功", songService.getSongByName(name)).getMessage();
        }catch (Exception e){
            throw new SystemException("系统繁忙，请稍后再试");
        }
    }
    //</editor-fold>


}
