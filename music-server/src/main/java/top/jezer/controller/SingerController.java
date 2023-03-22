package top.jezer.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import top.jezer.common.ErrorResp;
import top.jezer.common.SuccessResp;
import top.jezer.domain.Singer;
import top.jezer.domain.Song;
import top.jezer.exception.BusinessException;
import top.jezer.exception.SystemException;
import top.jezer.service.serviceImpl.SingerServiceImpl;

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
        String pic = "/img/singerPic/user.jpg";

        Singer singer = new Singer();
        if (StringUtils.isNotBlank(name)){
            singer.setName(name.trim());
            if (singerService.getSingerByNameEq(singer.getName()) != null)
                return new ErrorResp("已存在同名歌手").getMessage();
        } else return new ErrorResp("歌手姓名不能为空").getMessage();
        if (StringUtils.isNotBlank(sex))
            singer.setSex(Integer.parseInt(sex));
        if (StringUtils.isNotBlank(birth)){
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date myBirth = new Date();
            try {
                myBirth = dateFormat.parse(birth.trim());
            } catch (Exception e) {
                throw new BusinessException("日期格式转换有误，应为yyyy-MM-dd");
            }
            singer.setBirth(myBirth);
        }
        if (StringUtils.isNotBlank(location))
            singer.setLocation(location.trim());
        if (StringUtils.isNotBlank(introduction))
            singer.setIntroduction(introduction.trim());
        singer.setPic(pic);
        try {
            boolean res = singerService.addSinger(singer);
            if (res) {
                return new SuccessResp<>("添加成功").getMessage();
            } else {
                return new ErrorResp("添加失败").getMessage();
            }
        } catch (Exception e){
            throw  new SystemException();
        }
    }
    // 删除歌手
    @DeleteMapping("/delete/{id}")
    public Object deleteSinger(@PathVariable Integer id){
        try {
            boolean res = singerService.deleteSinger(id);
            if (res)
                return new SuccessResp<>("删除成功").getMessage();
            else return new ErrorResp("删除失败").getMessage();
        }catch (Exception e){
            throw new SystemException();
        }
    }
    // 返回所有歌手
    @GetMapping
    public Object getAllSinger(){
        try {
            return new SuccessResp<>("查询成功", singerService.getAllSinger()).getMessage();
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
    public Object getSingerByName(HttpServletRequest req){
        String name = req.getParameter("name").trim();
        try {
            return new SuccessResp<>("查询成功", singerService.getSingerByName(name)).getMessage();
        }catch (Exception e){
            throw new SystemException();
        }
    }
    // 根据歌手性别查找
    @GetMapping("/sex/detail")
    public Object getSingerBySex(HttpServletRequest req){
        String sex = req.getParameter("sex").trim();
        try {
            return new SuccessResp<>("查询成功", singerService.getSingerBySex(Integer.parseInt(sex))).getMessage();
        }catch (Exception e){
            throw new SystemException();
        }
    }
    // 更新歌手信息
    @PutMapping("/update")
    public Object updateSinger(HttpServletRequest req){
        Singer singer = new Singer();
        String id = req.getParameter("id");
        String name = req.getParameter("name");
        String sex = req.getParameter("sex");
        String birth = req.getParameter("birth");
        String location = req.getParameter("location");
        String introduction = req.getParameter("introduction");
        if (StringUtils.isNotBlank(id)){
            singer.setId(Integer.parseInt(id.trim()));
        }else return new ErrorResp("id不能为空").getMessage();
        if (StringUtils.isNotBlank(name))
            singer.setName(name.trim());
        if (StringUtils.isNotBlank(sex))
            singer.setSex(Integer.parseInt(sex.trim()));
        if (StringUtils.isNotBlank(birth)){
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date myBirth = new Date();
            try {
                myBirth = dateFormat.parse(birth.trim());
            } catch (Exception e) {
                throw new BusinessException("日期格式转换有误，应为yyyy-MM-dd");
            }
            singer.setBirth(myBirth);
        }
        if (StringUtils.isNotBlank(location))
            singer.setLocation(location.trim());
        if (StringUtils.isNotBlank(introduction))
            singer.setIntroduction(introduction.trim());
        try {
            boolean res = singerService.updateSinger(singer);
            if (res) {
                return new SuccessResp<>("更新成功").getMessage();
            } else {
                return new ErrorResp("更新失败").getMessage();
            }
        } catch (Exception e){
            throw  new SystemException();
        }
    }
}
