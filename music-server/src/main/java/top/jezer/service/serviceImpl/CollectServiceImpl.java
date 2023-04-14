package top.jezer.service.serviceImpl;

import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jezer.controller.dto.CollectDTO;
import top.jezer.domain.Collect;
import top.jezer.domain.Song;
import top.jezer.mapper.CollectMapper;
import top.jezer.service.CollectService;

import java.util.List;

@Service
public class CollectServiceImpl implements CollectService {
    @Autowired
    private CollectMapper collectMapper;
    @Override
    public List<CollectDTO> getCollectSongByUserId(Integer userId) {
        MPJLambdaWrapper<Collect> wrapper = new MPJLambdaWrapper<Collect>()
                .select(Collect::getId)
                .select(Song::getPic, Song::getName)
                .leftJoin(Song.class ,Song::getId,  Collect::getSongId)
                .eq(Collect::getType, 0).eq(Collect::getUserId, userId);
        return collectMapper.selectJoinList(CollectDTO.class, wrapper);
    }

    @Override
    public boolean deleteCollect(Integer id) {
        return collectMapper.deleteById(id) > 0;
    }

    @Override
    public boolean deleteCollects(List<Integer> ids) {
        return collectMapper.deleteBatchIds(ids) > 0;
    }
}
