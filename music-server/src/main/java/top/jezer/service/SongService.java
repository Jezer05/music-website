package top.jezer.service;

import org.springframework.transaction.annotation.Transactional;
import top.jezer.domain.Song;

import java.util.List;

@Transactional
public interface SongService {
    // 添加新歌曲
    boolean addSong (Song song);
    // 更新歌曲信息
    boolean updateSong(Song song);
    // 删除歌曲
    boolean deleteSong(Integer id);
    // 获取所有歌曲
    List<Song> getAllSong();
    // 根据歌手Id查找歌曲
    List<Song> getSongBySingerId(Integer singerId);
    // 根据歌曲Id查找
    Song getSongById(Integer id);
    // 根据歌曲名模糊匹配
    List<Song> getSongByName(String name);
    // 根据歌曲名精确匹配
    Song getSongByNameEq(String name);
}
