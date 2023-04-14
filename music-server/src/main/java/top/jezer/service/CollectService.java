package top.jezer.service;

import org.springframework.transaction.annotation.Transactional;
import top.jezer.controller.dto.CollectDTO;

import java.util.List;

@Transactional
public interface CollectService {
    // 获得用户的收藏歌曲
    List<CollectDTO> getCollectSongByUserId(Integer userId);
    // 删除
    boolean deleteCollect(Integer id);
    boolean deleteCollects(List<Integer> ids);
}
