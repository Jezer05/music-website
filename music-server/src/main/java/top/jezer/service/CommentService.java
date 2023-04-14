package top.jezer.service;

import org.springframework.transaction.annotation.Transactional;
import top.jezer.controller.dto.CommentDTO;

import java.util.List;

@Transactional
public interface CommentService {
    // 根据歌曲Id获得评论
    List<CommentDTO> getCommentBySongId(Integer songId);
    // 根据歌单Id获得评论
    List<CommentDTO> getCommentBySongListId(Integer songListId);
    // 删除评论
    boolean deleteComment(Integer id);
    boolean deleteComments(List<Integer> ids);
}
