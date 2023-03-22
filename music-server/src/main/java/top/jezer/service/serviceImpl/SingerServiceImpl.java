package top.jezer.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jezer.domain.Singer;
import top.jezer.mapper.SingerMapper;
import top.jezer.service.SingerService;

import java.util.List;

@Service
public class SingerServiceImpl implements SingerService {
    @Autowired
    private SingerMapper singerMapper;
    @Override
    public boolean addSinger(Singer singer) {
        return singerMapper.insert(singer) > 0;
    }
    @Override
    public boolean updateSinger(Singer singer) {
        return singerMapper.updateById(singer) > 0;
    }

    @Override
    public boolean deleteSinger(Integer id) {
        return singerMapper.deleteById(id) > 0;
    }

    @Override
    public List<Singer> getAllSinger() {
        return singerMapper.selectList(null);
    }

    @Override
    public Singer getSingerById(Integer id) {
        return singerMapper.selectById(id);
    }

    @Override
    public List<Singer> getSingerByName(String name) {
        LambdaQueryWrapper<Singer> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(Singer::getName, name);
        return singerMapper.selectList(wrapper);
    }

    @Override
    public Singer getSingerByNameEq(String name) {
        LambdaQueryWrapper<Singer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Singer::getName, name);
        return singerMapper.selectOne(wrapper);
    }

    @Override
    public List<Singer> getSingerBySex(Integer sex) {
        LambdaQueryWrapper<Singer> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Singer::getSex, sex);
        return singerMapper.selectList(wrapper);
    }
}
