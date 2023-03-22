package top.jezer.controller;

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

import java.io.File;
import java.io.IOException;

@RestController
@RequestMapping("/songs")
public class SongController {
    @Autowired
    private SongService songService;
    // 添加新歌曲
    @PostMapping("/add")
    public Object addSong(Song song, @RequestParam("file") MultipartFile file) {
        //  禁止用户自定义ID
        song.setId(null);
        song.setPic("/img/songPic/tubiao.jpg");
        // String会自动转成Integer
        if (null == song.getSingerId() || StringUtils.isBlank(song.getName()))
            return new ErrorResp("歌手id和歌曲名称不能为空").getMessage();
        // 创建音乐资源路径
        String fileName = file.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + ResourceLocation.ASSETS_PATH + "/song";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeUrlPath = "/song/" + fileName;
        song.setUrl(storeUrlPath);
        // TODO 查看是否存在同名歌曲
        try {
            file.transferTo(dest);
            boolean res = songService.addSong(song);
            if (res) {
                return new SuccessResp<>("歌曲添加成功").getMessage();
            } else {
                return new ErrorResp("歌曲添加失败").getMessage();
            }
        } catch (Exception e) {
            throw new SystemException("系统繁忙，请稍后再试");
        }
    }
    // 删除歌曲
    @DeleteMapping("/delete/{id}")
    public Object deleteSong(@PathVariable("id") Integer id){
        try{
            boolean res = songService.deleteSong(id);
            if (res) {
                return new SuccessResp<>("歌曲删除成功").getMessage();
            } else {
                return new ErrorResp("歌曲删除失败").getMessage();
            }
        }catch(Exception e){
            throw new SystemException("系统繁忙，请稍后再试");
        }
    }
    // 查询所有歌曲
    @GetMapping
    public Object getAllSong(){
        try{
            return new SuccessResp<>("歌曲查询成功", songService.getAllSong()).getMessage();
        }catch (Exception e){
            throw new SystemException("系统繁忙，请稍后再试");
        }
    }
    // 返回指定歌曲ID的歌曲
    @GetMapping("/{id}")
    public Object getSongById(@PathVariable("id") Integer id){
        try{
            // 没有数据也是查询成功，只是data为null
            return new SuccessResp<>("歌曲查询成功", songService.getSongById(id)).getMessage();
        }catch (Exception e){
            throw new SystemException("系统繁忙，请稍后再试");
        }
    }
    // 根据歌手Id查询歌曲
    @GetMapping("/singer/detail/{id}")
    public Object getSongBySingerId(@PathVariable("id") Integer singerId){
        try{
            return new SuccessResp<>("歌曲查询成功",songService.getSongBySingerId(singerId)).getMessage();
        }catch (Exception e){
            throw new SystemException("系统繁忙，请稍后再试");
        }
    }
    // 根据歌手查询歌曲
    @GetMapping("/singerName/detail")
    public Object getSongByName(String name){
        try{
            return new SuccessResp<>("歌曲查询成功", songService.getSongByName(name)).getMessage();
        }catch (Exception e){
            throw new SystemException("系统繁忙，请稍后再试");
        }
    }
    // 更新歌曲信息
    @PutMapping("/update")
    public Object updateSongMsg(Song song) {
        if (null == song.getId())
            return new ErrorResp("歌曲id不能为空").getMessage();
        try {
            boolean res = songService.updateSong(song);
            if (res) {
                return new SuccessResp<>("歌曲信息修改成功").getMessage();
            } else {
                return new ErrorResp("歌曲信息修改失败").getMessage();
            }
        }catch (Exception e){
            throw new SystemException("系统繁忙，请稍后再试");
        }
    }
    // 更新歌曲图片
    @PutMapping("/updateImg/{id}")
    public Object updateSongPic(@RequestParam("file") MultipartFile file, @PathVariable("id") Integer id) {
        // TODO 删除旧图片
        String fileName = System.currentTimeMillis() + file.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + ResourceLocation.ASSETS_PATH + "/img/songPic";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeUrlPath = "/img/songPic/" + fileName;
        try {
            file.transferTo(dest);
            Song song = new Song();
            song.setId(id);
            song.setPic(storeUrlPath);
            boolean res = songService.updateSong(song);
            if (res) {
                return new SuccessResp<>("歌曲图片上传成功", storeUrlPath).getMessage();
            } else {
                return new ErrorResp("歌曲图片上传失败").getMessage();
            }
        } catch (IOException e) {
            return new SystemException();
        }
    }

    // 更新歌曲资源
    @PutMapping("/updateUrl/{id}")
    public Object updateSongUrl(@RequestParam("file") MultipartFile file, @PathVariable("id") Integer id) {
        // TODO 删除旧资源
        String fileName = file.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + ResourceLocation.ASSETS_PATH + "/song";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeUrlPath = "/song/" + fileName;
        try {
            file.transferTo(dest);
            Song song = new Song();
            song.setId(id);
            song.setUrl(storeUrlPath);
            boolean res = songService.updateSong(song);
            if (res) {
                return new SuccessResp<>("上传成功", storeUrlPath).getMessage();
            } else {
                return new ErrorResp("上传失败").getMessage();
            }
        } catch (IOException e) {
            return new SystemException();
        }
    }
}
