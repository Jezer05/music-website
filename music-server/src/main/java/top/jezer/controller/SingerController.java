package top.jezer.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.jezer.common.ErrorResp;
import top.jezer.common.SuccessResp;
import top.jezer.constant.ResourceLocation;
import top.jezer.domain.Singer;
import top.jezer.exception.BusinessException;
import top.jezer.exception.SystemException;
import top.jezer.service.serviceImpl.SingerServiceImpl;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/singers")
public class SingerController {
    @Autowired
    private SingerServiceImpl singerService;
    // 添加歌手
    @PostMapping
    public Object addSinger(@RequestBody Singer singer){
        // 数据类型已在前端处理和判空
        String name = singer.getName().trim();
        try {
            if (singerService.getSingerByNameEq(name) != null)
                return new ErrorResp("已存在同名歌手");
        } catch (Exception e){
            throw new SystemException();
        }
        singer.setPic("/img/singerPic/user.jpg");
        try {
            boolean res = singerService.addSinger(singer);
            if (res) {
                return new SuccessResp("歌手添加成功");
            } else {
                return new ErrorResp("歌手添加失败");
            }
        } catch (Exception e){
            throw  new SystemException();
        }
    }
    // 删除歌手
    @DeleteMapping("/{id}")
    public Object deleteSinger(@PathVariable Integer id){
        //if (null == singerService.getSingerById(id))
        //    return new ErrorResp("查询不到对应歌手");
        try {
            boolean res = singerService.deleteSinger(id);
            if (res)
                return new SuccessResp("歌手删除成功");
            else return new ErrorResp("歌手删除失败");
        }catch (Exception e){
            throw new SystemException();
        }
    }
    // 批量删除歌手
    @DeleteMapping
    public Object deleteSingers(@RequestBody List<Integer> ids){
        System.out.println(ids);
        try {
            boolean res = singerService.deleteSingers(ids);
            if (res)
                return new SuccessResp("歌手删除成功");
            else return new ErrorResp("歌手删除失败");
        }catch (Exception e){
            throw new SystemException();
        }
    }
    // 返回所有歌手
    @GetMapping
    public Object getAllSinger(){
        try {
            return new SuccessResp("歌手查询成功", singerService.getAllSinger());
        }catch (Exception e){
            throw  new SystemException();
        }
    }
    // 更新歌手信息
    @PutMapping("/{id}")
    public Object updateSinger(@RequestBody Singer singer, @PathVariable Integer id){
        singer.setId(id);
        try {
            boolean res = singerService.updateSinger(singer);
            if (res) {
                return new SuccessResp("歌手信息更新成功");
            } else {
                return new ErrorResp("歌手信息更新失败");
            }
        } catch (Exception e){
            throw  new SystemException();
        }
    }
    // 更新歌手头像
    @PutMapping("/avatar/{id}")
    public Object updateSingerPic(@RequestParam("file") MultipartFile avatarFile, @PathVariable("id") Integer id) {
        String fileName = System.currentTimeMillis() + avatarFile.getOriginalFilename();
        String filePath = System.getProperty("user.dir") + ResourceLocation.ASSETS_PATH + "/img/singerPic";
        File file1 = new File(filePath);
        if (!file1.exists()) {
            file1.mkdir();
        }
        File dest = new File(filePath + System.getProperty("file.separator") + fileName);
        String imgPath = "/img/singerPic/" + fileName;
        try {
            avatarFile.transferTo(dest);
            Singer singer = new Singer();
            singer.setId(id);
            singer.setPic(imgPath);
            boolean res = singerService.updateSinger(singer);
            if (res) {
                return new SuccessResp("上传成功", imgPath);
            } else {
                return new ErrorResp("上传失败");
            }
        } catch (IOException e) {
            return new SystemException();
        }
    }
}
