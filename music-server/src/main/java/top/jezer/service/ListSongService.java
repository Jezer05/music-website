package top.jezer.service;

import org.springframework.transaction.annotation.Transactional;
import top.jezer.controller.dto.ListSongDTO;
import top.jezer.domain.Song;


import java.util.List;

@Transactional
public interface ListSongService {
    //  根据歌单Id返回对应歌曲
    List<ListSongDTO> getSongListByListId(Integer songListId);
    // 删除
    boolean deleteListSong(Integer id);
    boolean deleteListSongs(List<Integer> ids);
}

