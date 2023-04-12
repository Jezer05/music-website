package top.jezer.mapper;

import com.github.yulichang.base.MPJBaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.jezer.domain.Comment;

@Mapper
public interface CommentMapper extends MPJBaseMapper<Comment> {
}
