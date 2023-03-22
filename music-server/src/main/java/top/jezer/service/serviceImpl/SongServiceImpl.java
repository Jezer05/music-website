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
        String name = song.getName();
        String introduction = song.getIntroduction();
        String lyric = song.getLyric();
        if (null != name)
            name = name.trim();
        if (null != introduction)
            introduction = introduction.trim();
        if (null != lyric)
            lyric = lyric.trim();
        song.setName(name);
        song.setIntroduction(introduction);
        song.setLyric(lyric);
        return songMapper.insert(song) > 0;
    }

    @Override
    public boolean updateSong(Song song) {
        // 去除首尾空格，trim不适用于null
        String name = song.getName();
        String introduction = song.getIntroduction();
        String lyric = song.getLyric();
        String pic = song.getPic();
        String url = song.getUrl();
        if (null != name)
            name = name.trim();
        if (null != introduction)
            introduction = introduction.trim();
        if (null != lyric)
            lyric = lyric.trim();
        // 如果pic或者url为空字符串，则统一设为null，不进行数据更新
        if (StringUtils.isBlank(pic))
            pic = null;
        if (StringUtils.isBlank(url))
            url = null;
        song.setName(name);
        song.setIntroduction(introduction);
        song.setLyric(lyric);
        song.setPic(pic);
        song.setUrl(url);
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
        if (null != name)
            name = name.trim();
        // 在url携带参数会删除多余空格，但是body中不会，空白字符默认查询所有
        if (StringUtils.isBlank(name))
            name = "";
        System.out.println(StringUtils.isBlank(name));
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
