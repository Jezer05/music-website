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
    //<editor-fold desc="增">
    @Override
    public boolean addSong(Song song) {
        song.setName(song.getName().trim());
        song.setIntroduction(song.getIntroduction().trim());
        song.setLyric(song.getLyric().trim());
        return songMapper.insert(song) > 0;
    }
    //</editor-fold>

    //<editor-fold desc="删">
    @Override
    public boolean deleteSong(Integer id) {
        return songMapper.deleteById(id) > 0;
    }

    @Override
    public boolean deleteSongs(List<Integer> ids) {
        return songMapper.deleteBatchIds(ids) > 0;
    }
    //</editor-fold>

    //<editor-fold desc="改">
    @Override
    public boolean updateSong(Song song) {
        String name = song.getName();
        String introduction = song.getIntroduction();
        String lyric = song.getLyric();
        // 更新头像和资源需要过滤null
        if (null != name)
            song.setName(name.trim());
        if (null != introduction)
            song.setIntroduction(introduction.trim());
        if (null != lyric)
            song.setLyric(lyric.trim());
        return songMapper.updateById(song) > 0;
    }
    //</editor-fold>

    //<editor-fold desc="查">
    @Override
    public List<Song> getAllSong() {
        return songMapper.selectList(null);
    }
    // 根据歌手id查找
    @Override
    public List<Song> getSongBySingerId(Integer singerId) {
        LambdaQueryWrapper<Song> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Song::getSingerId, singerId);
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
    //</editor-fold>

    //<editor-fold desc="暂未使用">
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
    public List<Song> getSongs(List<Integer> ids) {
        return songMapper.selectBatchIds(ids);
    }
//</editor-fold>



}
