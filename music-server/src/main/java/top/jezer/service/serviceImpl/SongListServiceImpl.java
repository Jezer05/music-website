package top.jezer.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jezer.domain.SongList;
import top.jezer.mapper.SongListMapper;
import top.jezer.service.SongListService;

import java.util.List;

@Service
public class SongListServiceImpl implements SongListService {
    @Autowired
    private SongListMapper songListMapper;
    //<editor-fold desc="增">
    @Override
    public boolean addSongList(SongList songList) {
        songList.setTitle(songList.getTitle().trim());
        songList.setIntroduction(songList.getIntroduction().trim());
        songList.setStyle(songList.getStyle().trim());
        return songListMapper.insert(songList) > 0;
    }
    //</editor-fold>

    //<editor-fold desc="删">
    @Override
    public boolean deleteSongList(Integer id) {
        return songListMapper.deleteById(id) > 0;
    }

    @Override
    public boolean deleteSongLists(List<Integer> ids) {
        return songListMapper.deleteBatchIds(ids) > 0;
    }
    //</editor-fold>

    //<editor-fold desc="改">
    @Override
    public boolean updateSongList(SongList songList) {
        String title = songList.getTitle();
        String introduction = songList.getIntroduction();
        String type = songList.getStyle();
        if (title != null)
            songList.setTitle(title.trim());
        if (introduction != null)
            songList.setIntroduction(introduction.trim());
        if (type != null)
            songList.setStyle(type.trim());
        return songListMapper.updateById(songList) > 0;
    }

    //</editor-fold>

    //<editor-fold desc="查">
    @Override
    public List<SongList> getAllSongList() {
        return songListMapper.selectList(null) ;
    }

    @Override
    public SongList getSongListByTitleEq(String title) {
        LambdaQueryWrapper<SongList> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SongList::getTitle, title);
        return songListMapper.selectOne(wrapper);
    }

    @Override
    public SongList getSongList(Integer id) {
        return songListMapper.selectById(id);
    }

    @Override
    public List<SongList> getSongLists(List<Integer> ids) {
        return songListMapper.selectBatchIds(ids);
    }
    //</editor-fold>


}
