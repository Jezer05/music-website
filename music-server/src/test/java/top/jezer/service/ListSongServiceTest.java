package top.jezer.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import top.jezer.service.serviceImpl.ListSongServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ListSongServiceTest {
    @Autowired
    private ListSongServiceImpl listSongService;

    @Test
    void getSongListByListId() {
        System.out.println(listSongService.getSongListByListId(1));
    }
}