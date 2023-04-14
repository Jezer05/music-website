package top.jezer.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import top.jezer.service.serviceImpl.CollectServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CollectServiceTest {
    @Autowired
    private CollectServiceImpl collectService;
    @Test
    void getCollectSongByUserId() {
        System.out.println(collectService.getCollectSongByUserId(5));
    }
}