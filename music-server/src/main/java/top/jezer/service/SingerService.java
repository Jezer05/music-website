package top.jezer.service;

import org.springframework.transaction.annotation.Transactional;
import top.jezer.domain.Singer;

import java.util.List;

@Transactional
public interface SingerService {
    // 添加歌手
    boolean addSinger(Singer singer);
    // 更新歌手信息
    boolean updateSinger(Singer singer);
    // 删除歌手
    boolean deleteSinger(Integer id);
    // 获得所有歌手
    List<Singer>  getAllSinger();
    // 根据id获得歌手信息
    Singer getSingerById(Integer id);
    // 根据歌手名字模糊匹配
    List<Singer> getSingerByName(String name);
    // 根据歌手名字精确匹配
    Singer getSingerByNameEq(String name);
    // 根据性别查询
    List<Singer> getSingerBySex(Integer sex);
}
