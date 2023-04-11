package top.jezer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.jezer.common.ErrorResp;
import top.jezer.common.SuccessResp;
import top.jezer.domain.Singer;
import top.jezer.exception.SystemException;
import top.jezer.service.serviceImpl.SingerServiceImpl;
import top.jezer.utils.FileUtils;

import java.util.List;

@RestController
@RequestMapping("/singers")
public class SingerController {
    @Autowired
    private SingerServiceImpl singerService;

    //<editor-fold desc="增">
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
    //</editor-fold>

    //<editor-fold desc="删">
    // 删除歌手
    @DeleteMapping("/{id}")
    public Object deleteSinger(@PathVariable Integer id){
        try {
            boolean res = singerService.deleteSinger(id);
            if (res)
                return new SuccessResp("歌手删除成功");
            else return new ErrorResp("未找到对应歌手信息，请刷新后再试");
        }catch (Exception e){
            throw new SystemException();
        }
    }
    // 批量删除歌手
    @DeleteMapping
    public Object deleteSingers(@RequestBody List<Integer> ids){
        try {
            boolean res = singerService.deleteSingers(ids);
            if (res)
                return new SuccessResp("歌手删除成功");
            else return new ErrorResp("未找到对应歌手信息，请刷新后再试");
        }catch (Exception e){
            throw new SystemException();
        }
    }
    //</editor-fold>

    //<editor-fold desc="改">
    // 更新歌手信息
    @PutMapping("/{id}")
    public Object updateSinger(@RequestBody Singer singer, @PathVariable Integer id){
        singer.setId(id);
        try {
            boolean res = singerService.updateSinger(singer);
            if (res) {
                return new SuccessResp("歌手信息更新成功");
            } else {
                return new ErrorResp("未找到对应歌手信息，请刷新后再试");
            }
        } catch (Exception e){
            throw  new SystemException();
        }
    }
    // 更新歌手头像
    @PutMapping("/avatar/{id}")
    public Object updateSingerPic(@RequestParam("file") MultipartFile avatarFile, @PathVariable("id") Integer id) {
        String imgPath = FileUtils.uploadFile(avatarFile, 1);
        Singer singer = new Singer();
        singer.setId(id);
        singer.setPic(imgPath);
        try {
            boolean res = singerService.updateSinger(singer);
            if (res) {
                return new SuccessResp("上传成功", imgPath);
            } else {
                return new ErrorResp("未找到对应歌手信息，请刷新后再试");
            }
        } catch (Exception e) {
            return new SystemException();
        }
    }
    //</editor-fold>

    //<editor-fold desc="查">
    // 返回所有歌手
    @GetMapping
    public Object getAllSinger(){
        try {
            return new SuccessResp("歌手查询成功", singerService.getAllSinger());
        }catch (Exception e){
            throw  new SystemException();
        }
    }
    //</editor-fold>


}
