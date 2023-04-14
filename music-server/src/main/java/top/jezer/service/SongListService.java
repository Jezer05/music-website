package top.jezer.service;

import org.springframework.transaction.annotation.Transactional;
import top.jezer.domain.SongList;

import java.util.List;

@Transactional
public interface SongListService {
    // 获得所有歌单信息
    List<SongList> getAllSongList();
    // 更新歌单信息
    boolean updateSongList(SongList songList);
    // 添加歌单信息
    boolean addSongList(SongList songList);
    // 删除歌单信息
    boolean deleteSongList(Integer id);
    boolean deleteSongLists(List<Integer> ids);
    SongList getSongListByTitleEq(String title);
}
