package top.jezer.service;

import org.springframework.transaction.annotation.Transactional;
import top.jezer.controller.dto.CollectListDTO;
import top.jezer.controller.dto.CollectSongDTO;
import top.jezer.domain.Collect;

import java.util.List;

@Transactional
public interface CollectService {
    // 获得用户的收藏歌曲
    List<CollectSongDTO> getCollectSongByUserId(Integer userId);
    // 获得用户的收藏歌单
    List<CollectListDTO> getCollectListByUserId(Integer userId);
    // 删除
    boolean deleteCollect(Integer id);
    boolean deleteCollects(List<Integer> ids);
    // 收藏歌曲
    boolean addCollect(Collect collect);
    // 取消收藏歌曲
    boolean delCollectSongBySongId(Integer id, Integer songId);
    // 取消收藏歌单
    boolean delCollectListByListId(Integer id, Integer ListId);

}
