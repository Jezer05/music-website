package top.jezer.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import top.jezer.domain.SongList;
import top.jezer.service.serviceImpl.SongListServiceImpl;
import top.jezer.service.serviceImpl.SongServiceImpl;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class SongListServiceTest {
    @Autowired
    private SongListServiceImpl songListService;
    @Test
    void getAllSongList() {
        System.out.println(songListService.getAllSongList().size());
    }

    @Test
    void updateSongList() {
        SongList songList = new SongList();
        songList.setId(1);;
        songList.setIntroduction("test01");
        System.out.println(songListService.updateSongList(songList));
    }

    @Test
    void addSongList() {
    }

    @Test
    void deleteSongList() {
    }

    @Test
    void deleteSongLists() {
    }

    @Test
    void getSongListByTitleEq() {
    }
}