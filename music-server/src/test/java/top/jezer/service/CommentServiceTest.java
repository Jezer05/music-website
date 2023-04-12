package top.jezer.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import top.jezer.service.serviceImpl.CommentServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class CommentServiceTest {
    @Autowired
    private CommentServiceImpl commentService;
    @Test
    void getCommentBySongId() {
        System.out.println(commentService.getCommentBySongId(21));
    }
}