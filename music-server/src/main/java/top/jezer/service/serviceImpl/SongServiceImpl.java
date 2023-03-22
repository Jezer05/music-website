package top.jezer.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.commons.lang3.StringUtils;
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
        // 去除首尾空格，trim不适用于null
        // 不进行Pic和URL的修改
        String introduction = song.getIntroduction();
        String lyric = song.getLyric();
        if (null != introduction)
            introduction = introduction.trim();
        if (null != lyric)
            lyric = lyric.trim();
        song.setIntroduction(introduction);
        song.setLyric(lyric);
        return songMapper.insert(song) > 0;
    }

    @Override
    public boolean updateSong(Song song) {
        // 去除首尾空格，trim不适用于null
        // 不进行Pic和URL的修改
        String name = song.getName();
        String introduction = song.getIntroduction();
        String lyric = song.getLyric();
        // name为空串时，默认不修改名称
        if (name.isBlank())
            name = null;
        else name = name.trim();
        if (null != introduction)
            introduction = introduction.trim();
        if (null != lyric)
            lyric = lyric.trim();
        song.setName(name);
        song.setIntroduction(introduction);
        song.setLyric(lyric);
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
        name = name.trim();
        // 在url携带参数会删除多余空格，但是body中不会，空白字符默认查询所有
        if (StringUtils.isBlank(name))
            name = "";
        LambdaQueryWrapper<Song> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Song::getName, name);
        return songMapper.selectList(wrapper);
    }

    @Override
    public Song getSongByNameEq(String name) {
        if (StringUtils.isNotBlank(name))
            name = name.trim();
        LambdaQueryWrapper<Song> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Song::getName, name);
        return songMapper.selectOne(wrapper);
    }
}
