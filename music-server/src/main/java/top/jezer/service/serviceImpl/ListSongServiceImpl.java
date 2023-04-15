package top.jezer.service.serviceImpl;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jezer.controller.dto.ListSongDTO;
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
    public List<ListSongDTO> getSongListByListId(Integer songListId) {
        MPJLambdaWrapper<ListSong> wrapper = new MPJLambdaWrapper<ListSong>()
                .select(ListSong::getId)
                .select(Song::getName, Song::getSingerId, Song::getIntroduction, Song::getLyric, Song::getPic, Song::getUrl)
                .selectAs(Song::getId, "songId")
                .leftJoin(Song.class, Song::getId, ListSong::getSongId)
                .eq(ListSong::getSongListId, songListId);
        return listSongMapper.selectJoinList(ListSongDTO.class, wrapper);
    }

    @Override
    public boolean deleteListSong(Integer id) {
        return listSongMapper.deleteById(id) > 0;
    }

    @Override
    public boolean deleteListSongs(List<Integer> ids) {
        return listSongMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public boolean addSongsIntoList(List<ListSong> list) {
        int count = 0;
        for (ListSong item: list) {
            count += listSongMapper.insert(item);
        }
        return count > 0;
    }
}
