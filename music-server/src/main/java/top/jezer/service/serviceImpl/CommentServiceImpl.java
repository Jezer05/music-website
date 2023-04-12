package top.jezer.service.serviceImpl;

import com.github.yulichang.query.MPJLambdaQueryWrapper;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.jezer.controller.dto.CommentDTO;
import top.jezer.domain.Comment;
import top.jezer.domain.Consumer;
import top.jezer.mapper.CommentMapper;
import top.jezer.service.CommentService;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentMapper commentMapper;

    @Override
    public List<CommentDTO> getCommentBySongId(Integer songId) {
        MPJLambdaWrapper<Comment> wrapper = new MPJLambdaWrapper<Comment>()
                .select(Comment::getId, Comment::getContent)
                .select(Consumer::getUsername)
                .leftJoin(Consumer.class, Consumer::getId, Comment::getUserId)
                .eq(Comment::getType, 0).eq(Comment::getSongId, songId);
        return commentMapper.selectJoinList(CommentDTO.class, wrapper);
    }

    @Override
    public boolean deleteComment(Integer id) {
        return commentMapper.deleteById(id) > 0;
    }

    @Override
    public boolean deleteComments(List<Integer> ids) {
        return commentMapper.deleteBatchIds(ids) > 0;
    }
}
