package top.jezer.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.jezer.domain.Song;

@Mapper
public interface SongMapper extends BaseMapper<Song> {
}
