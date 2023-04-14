package top.jezer.service.serviceImpl;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jezer.domain.ListSong;
import top.jezer.domain.Song;
import top.jezer.domain.SongList;
import top.jezer.mapper.ListSongMapper;
import top.jezer.service.ListSongService;

import java.util.List;

@Service
public class ListSongServiceImpl implements ListSongService {
    @Autowired
    private ListSongMapper listSongMapper;
    @Override
    public List<Song> getSongListByListId(Integer songListId) {
        MPJLambdaWrapper<ListSong> wrapper = new MPJLambdaWrapper<ListSong>()
                .selectAll(Song.class)
                .leftJoin(Song.class, Song::getId, ListSong::getSongId)
                .eq(ListSong::getSongListId, songListId);
        return listSongMapper.selectJoinList(Song.class, wrapper);
    }
}
