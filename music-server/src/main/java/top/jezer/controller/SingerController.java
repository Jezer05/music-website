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

@RestController
@RequestMapping("/singers")
public class SingerController {
    @Autowired
    private SingerServiceImpl singerService;
    // 添加歌手
    @PostMapping("/add")
    public Object addSinger(HttpServletRequest req){
        String name = req.getParameter("name");
        String sex = req.getParameter("sex");
        String birth = req.getParameter("birth");
        String location = req.getParameter("location");
        String introduction = req.getParameter("introduction");
        // 歌手姓名不能为空串
        if (StringUtils.isNotBlank(name)){
            name = name.trim();
            try {
                if (singerService.getSingerByNameEq(name) != null)
                    return new ErrorResp("已存在同名歌手").getMessage();
            } catch (Exception e){
                throw new SystemException();
            }
        } else return new ErrorResp("歌手姓名不能为空").getMessage();
        if (!(null == sex || sex.trim().equals("0") || sex.trim().equals("1")))
            return new ErrorResp("性别数据输入不合法").getMessage();
        Singer singer = new Singer();
        singer.setName(name);
        if (sex == null)
            singer.setSex(null);
        else singer.setSex(Integer.parseInt(sex));
        singer.setPic("/img/singerPic/user.jpg");
        if (StringUtils.isNotBlank(birth)) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date myBirth;
            try {
                myBirth = dateFormat.parse(birth.trim());
                singer.setBirth(myBirth);
            } catch (Exception e) {
                throw new BusinessException("日期格式转换有误，应为yyyy-MM-dd");
            }
        } else singer.setBirth(null);
        singer.setLocation(location);
        singer.setIntroduction(introduction);
        try {
            boolean res = singerService.addSinger(singer);
            if (res) {
                return new SuccessResp<>("歌手添加成功").getMessage();
            } else {
                return new ErrorResp("歌手添加失败").getMessage();
            }
        } catch (Exception e){
            throw  new SystemException();
        }
    }
    // 删除歌手
    @DeleteMapping("/delete/{id}")
    public Object deleteSinger(@PathVariable Integer id){
        if (null == singerService.getSingerById(id))
            return new ErrorResp("查询不到对应歌手").getMessage();
        try {
            boolean res = singerService.deleteSinger(id);
            if (res)
                return new SuccessResp<>("歌手删除成功").getMessage();
            else return new ErrorResp("歌手删除失败").getMessage();
        }catch (Exception e){
            throw new SystemException();
        }
    }
    // 返回所有歌手
    @GetMapping
    public Object getAllSinger(){
        try {
            return new SuccessResp<>("歌手查询成功", singerService.getAllSinger()).getMessage();
        }catch (Exception e){
            throw  new SystemException();
        }
    }
    // 根据歌手id查找
    @GetMapping("/{id}")
    public Object getSingerById(@PathVariable Integer id){
        try {
            return new SuccessResp<>("查询成功", singerService.getSingerById(id)).getMessage();
        }catch (Exception e){
            throw new SystemException();
        }
    }

    // 根据歌手名查找
    @GetMapping("/name/detail")
    public Object getSingerByName(String name){
        if (null == name)
            return new ErrorResp("歌手名称不能为空").getMessage();
        try {
            return new SuccessResp<>("查询成功", singerService.getSingerByName(name)).getMessage();
        }catch (Exception e){
            throw new SystemException();
        }
    }
    // 根据歌手性别查找
    @GetMapping("/sex/detail/{sex}")
    public Object getSingerBySex(@PathVariable("sex") Integer sex){
        if (sex != 0 && sex != 1)
            return new ErrorResp("性别数据输入不合法").getMessage();
        try {
            return new SuccessResp<>("歌手查询成功", singerService.getSingerBySex(sex)).getMessage();
        }catch (Exception e){
            throw new SystemException();
        }
    }
    // 更新歌手信息
    @PutMapping("/update")
    public Object updateSinger(HttpServletRequest req){
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String sex = req.getParameter("sex");
        String birth = req.getParameter("birth");
        String location = req.getParameter("location");
        String introduction = req.getParameter("introduction");
        if (StringUtils.isBlank(id))
            return new ErrorResp("id不能为空").getMessage();
        if (!(null == sex || sex.trim().equals("0") || sex.trim().equals("1")))
            return new ErrorResp("性别数据输入不合法").getMessage();
        Singer singer = new Singer();
        singer.setId(Integer.parseInt(id.trim()));
        if (null == singerService.getSingerById(singer.getId()))
            return new ErrorResp("查询不到对应歌手").getMessage();
        singer.setName(name);
        if (sex == null)
            singer.setSex(null);
        else singer.setSex(Integer.parseInt(sex));
        if (StringUtils.isNotBlank(birth)) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date myBirth;
            try {
                myBirth = dateFormat.parse(birth.trim());
                singer.setBirth(myBirth);
            } catch (Exception e) {
                throw new BusinessException("日期格式转换有误，应为yyyy-MM-dd");
            }
        } else singer.setBirth(null);
        singer.setLocation(location);
        singer.setIntroduction(introduction);
        try {
            boolean res = singerService.updateSinger(singer);
            if (res) {
                return new SuccessResp<>("歌手信息更新成功").getMessage();
            } else {
                return new ErrorResp("歌手信息更新失败").getMessage();
            }
        } catch (Exception e){
            throw  new SystemException();
        }
    }
    // 更新歌手头像
    @PutMapping("/update/avatar/{id}")
    public Object updateSingerPic(@RequestParam("file") MultipartFile avatarFile, @PathVariable("id") Integer id) {
        if (null == singerService.getSingerById(id))
            return new ErrorResp("查询不到对应歌手").getMessage();
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
                return new SuccessResp<>("上传成功", imgPath).getMessage();
            } else {
                return new ErrorResp("上传失败").getMessage();
            }
        } catch (IOException e) {
            return new SystemException();
        }
    }
}