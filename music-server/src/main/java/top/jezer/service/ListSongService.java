package top.jezer.service;

import org.springframework.transaction.annotation.Transactional;
import top.jezer.domain.Song;


import java.util.List;

@Transactional
public interface ListSongService {
    //  根据歌单Id返回对应歌曲
    List<Song> getSongListByListId(Integer songListId);
}

