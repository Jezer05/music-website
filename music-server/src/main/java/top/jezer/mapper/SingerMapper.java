package top.jezer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.jezer.domain.Singer;

@Mapper
public interface SingerMapper extends BaseMapper<Singer> {
}
