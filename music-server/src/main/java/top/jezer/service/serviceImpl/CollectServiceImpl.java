package top.jezer.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jezer.controller.dto.CollectListDTO;
import top.jezer.controller.dto.CollectSongDTO;
import top.jezer.domain.Collect;
import top.jezer.domain.Song;
import top.jezer.domain.SongList;
import top.jezer.mapper.CollectMapper;
import top.jezer.service.CollectService;

import java.util.List;

@Service
public class CollectServiceImpl implements CollectService {
    @Autowired
    private CollectMapper collectMapper;
    @Override
    public List<CollectSongDTO> getCollectSongByUserId(Integer userId) {
        MPJLambdaWrapper<Collect> wrapper = new MPJLambdaWrapper<Collect>()
                .selectAs(Collect::getId, "collectId")
                .selectAll(Song.class)
                .leftJoin(Song.class ,Song::getId,  Collect::getSongId)
                .eq(Collect::getType, 0).eq(Collect::getUserId, userId);
        return collectMapper.selectJoinList(CollectSongDTO.class, wrapper);
    }

    @Override
    public List<CollectListDTO> getCollectListByUserId(Integer userId) {
        MPJLambdaWrapper<Collect> wrapper = new MPJLambdaWrapper<Collect>()
                .selectAs(Collect::getId, "collectId")
                .selectAll(SongList.class)
                .leftJoin(SongList.class ,SongList::getId,  Collect::getSongListId)
                .eq(Collect::getType, 1).eq(Collect::getUserId, userId);
        return collectMapper.selectJoinList(CollectListDTO.class, wrapper);
    }

    @Override
    public boolean deleteCollect(Integer id) {
        return collectMapper.deleteById(id) > 0;
    }

    @Override
    public boolean deleteCollects(List<Integer> ids) {
        return collectMapper.deleteBatchIds(ids) > 0;
    }

    @Override
    public boolean addCollect(Collect collect) {
        return collectMapper.insert(collect) > 0;
    }

    @Override
    public boolean delCollectSongBySongId(Integer id, Integer songId) {
        LambdaQueryWrapper<Collect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Collect::getType, 0).eq(Collect::getUserId, id).eq(Collect::getSongId, songId);
        return collectMapper.delete(wrapper) > 0;
    }


    @Override
    public boolean delCollectListByListId(Integer id, Integer ListId) {
        LambdaQueryWrapper<Collect> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Collect::getType, 1).eq(Collect::getSongListId, ListId).eq(Collect::getUserId, id);
        return collectMapper.delete((wrapper)) >  0;
    }
}
