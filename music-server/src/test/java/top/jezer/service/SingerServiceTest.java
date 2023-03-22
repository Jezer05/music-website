package top.jezer.service;

import org.apache.tomcat.util.buf.ByteBufferUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import top.jezer.domain.Singer;
import top.jezer.service.serviceImpl.SingerServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class SingerServiceTest {

    @Autowired
    private SingerServiceImpl singerService;
    @Test
    void addSinger() {
        Singer singer = new Singer();
        singer.setName("test");
        singer.setSex(1);
        System.out.println(singerService.addSinger(singer));
    }

    @Test
    void updateSinger() {
        Singer singer = new Singer();
        //singer.setId(1);
        singer.setName("test");
        singer.setSex(1);
        System.out.println(singerService.updateSinger(singer));
    }

    @Test
    void deleteSinger() {
        singerService.deleteSinger(1);
    }

    @Test
    void getAllSinger() {
        singerService.getAllSinger();
    }

    @Test
    void getSingerById() {
        singerService.getSingerById(1);
    }

    @Test
    void getSingerByName() {
        singerService.getSingerByName("杰");
    }

    @Test
    void getSingerByNameEq() {
        singerService.getSingerByNameEq("周杰伦");
    }

    @Test
    void getSingerBySex() {
        singerService.getSingerBySex(1);
    }
}