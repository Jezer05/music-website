package top.jezer.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jezer.domain.Song;
import top.jezer.mapper.SongMapper;
import top.jezer.service.SongService;

import java.util.List;

@Service
public class SongServiceImpl implements SongService {
    @Autowired
    private SongMapper songMapper;
    @Override
    public boolean addSong(Song song) {
        return songMapper.insert(song) > 0;
    }

    @Override
    public boolean updateSong(Song song) {
        return songMapper.updateById(song) > 0;
    }

    @Override
    public boolean deleteSong(Integer id) {
        return songMapper.deleteById(id) > 0;
    }

    @Override
    public List<Song> getAllSong() {
        return songMapper.selectList(null);
    }

    @Override
    public List<Song> getSongBySingerId(Integer singerId) {
        LambdaQueryWrapper<Song> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Song::getSingerId, singerId);
        return songMapper.selectList(wrapper);
    }

    @Override
    public Song getSongById(Integer id) {
        return songMapper.selectById(id);
    }

    @Override
    public List<Song> getSongByName(String name) {
        LambdaQueryWrapper<Song> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Song::getName, name);
        return songMapper.selectList(wrapper);
    }

    @Override
    public Song getSongByNameEq(String name) {
        LambdaQueryWrapper<Song> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Song::getName, name);
        return songMapper.selectOne(wrapper);
    }
}
