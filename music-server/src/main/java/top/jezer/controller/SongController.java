package top.jezer.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.jezer.common.ErrorResp;
import top.jezer.common.SuccessResp;
import top.jezer.common.WarningResp;
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
    public Object addSong(HttpServletRequest req, @RequestParam("file") MultipartFile mpfile) {
        // 资源判重，数据判空
        String singer_id = req.getParameter("singerId");
        String name = req.getParameter("name");
        String introduction = req.getParameter("introduction");
        String pic = "/img/songPic/tubiao.jpg";
        String lyric = req.getParameter("lyric");

        Song song = new Song();
        if (StringUtils.isNotBlank(singer_id) && StringUtils.isNotBlank(name)){
            song.setSingerId(Integer.parseInt(singer_id.trim()));
            song.setName(name.trim());
        }else return new ErrorResp("歌手id和歌曲名称不能为空").getMessage();
        if (StringUtils.isNotBlank(introduction))
            song.setIntroduction(introduction.trim());
        if (StringUtils.isNotBlank(lyric))
            song.setLyric(lyric.trim());
        song.setPic(pic);

        try {
            if (songService.getSongByNameEq(song.getName()) != null)
                return new ErrorResp("已存在重名资源！").getMessage();
        }catch (Exception e){
            throw new SystemException();
        }

        String fileName = mpfile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + ResourceLocation.ASSETS_PATH + "/song";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }

        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeUrlPath = "/song/" + fileName;
        try {
            song.setUrl(storeUrlPath);
            mpfile.transferTo(dest);
            boolean res = songService.addSong(song);
            if (res) {
                return new SuccessResp<>("上传成功", storeUrlPath).getMessage();
            } else {
                return new ErrorResp("上传失败").getMessage();
            }
        } catch (IOException e) {
            throw new SystemException("系统繁忙，请稍后再试");
        }
    }
    // 删除歌曲
    @DeleteMapping("/delete/{id}")
    public Object deleteSong(@PathVariable("id") int id){
        try{
            boolean res = songService.deleteSong(id);
            if (res) {
                return new SuccessResp<>("删除成功").getMessage();
            } else {
                return new ErrorResp("删除失败").getMessage();
            }
        }catch(Exception e){
            throw new SystemException("系统繁忙，请稍后再试");
        }
    }
    // 查询所有歌曲
    @GetMapping
    public Object getAllSong(){
        try{
            return new SuccessResp<>("查询成功", songService.getAllSong()).getMessage();
        }catch (Exception e){
            throw new SystemException("系统繁忙，请稍后再试");
        }
    }
    // 返回指定歌曲ID的歌曲
    @GetMapping("/detail/{id}")
    public Object getSongById(@PathVariable("id") int id){
        try{
            return new SuccessResp<>("查询成功", songService.getSongById(id)).getMessage();
        }catch (Exception e){
            throw new SystemException("系统繁忙，请稍后再试");
        }
    }
    // 根据歌手Id查询歌曲
    @GetMapping("/singer/detail/{id}")
    public Object getSongBySingerId(@PathVariable("id") int singerId){
        try{
            return new SuccessResp<>("查询成功",songService.getSongBySingerId(singerId)).getMessage();
        }catch (Exception e){
            throw new SystemException("系统繁忙，请稍后再试");
        }
    }
    // 根据歌手查询歌曲
    @GetMapping("/singerName/detail")
    public Object getSongByName(HttpServletRequest req){
        String name = req.getParameter("name");
        try{
            return new SuccessResp<>("查询成功", songService.getSongByName(name)).getMessage();
        }catch (Exception e){
            throw new SystemException("系统繁忙，请稍后再试");
        }
    }
    // 更新歌曲信息
    @PutMapping("/update")
    public Object updateSongMsg(HttpServletRequest req) {
        Song song = new Song();
        String id = req.getParameter("id");
        String singer_id = req.getParameter("singerId");
        String name = req.getParameter("name");
        String introduction = req.getParameter("introduction");
        String lyric = req.getParameter("lyric");
        if (StringUtils.isBlank(id))
            return new WarningResp("id不能为空").getMessage();
        else
            song.setId(Integer.parseInt(id.trim()));
        if (StringUtils.isNotBlank(singer_id))
            song.setSingerId(Integer.parseInt(singer_id.trim()));
        if (StringUtils.isNotBlank(name))
            song.setName(name.trim());
        if (StringUtils.isNotBlank(introduction))
            song.setIntroduction(introduction.trim());
        if (StringUtils.isNotBlank(lyric))
            song.setLyric(lyric.trim());
        try {
            boolean res = songService.updateSong(song);
            if (res) {
                return new SuccessResp<>("修改成功").getMessage();
            } else {
                return new ErrorResp("修改失败").getMessage();
            }
        }catch (Exception e){
            throw new SystemException("系统繁忙，请稍后再试");
        }
    }
    // 更新歌曲图片
    @PutMapping("/updateImg/{id}")
    public Object updateSongPic(@RequestParam("file") MultipartFile urlFile, @PathVariable("id") int id) {
        // TODO 删除旧图片
        String fileName = System.currentTimeMillis() + urlFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + ResourceLocation.ASSETS_PATH + "/img/songPic";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeUrlPath = "/img/songPic/" + fileName;
        try {
            urlFile.transferTo(dest);
            Song song = new Song();
            song.setId(id);
            song.setPic(storeUrlPath);
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

    // 更新歌曲资源
    @PutMapping("/updateUrl/{id}")
    public Object updateSongUrl(@RequestParam("file") MultipartFile urlFile, @PathVariable("id") int id) {
        // TODO 删除旧资源
        String fileName = urlFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + ResourceLocation.ASSETS_PATH + "/song";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String storeUrlPath = "/song/" + fileName;
        try {
            urlFile.transferTo(dest);
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
