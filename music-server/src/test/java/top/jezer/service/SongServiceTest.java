package top.jezer.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import top.jezer.domain.Song;

import java.util.List;

@SpringBootTest
@Transactional
public class SongServiceTest {
    @Autowired
    private SongService songService;
    @Test
    public void addSongTest (){
        Song song = new Song();
        song.setSingerId(2);
        song.setName("test");
        song.setUrl("test");
        songService.addSong(song);
    }
    @Test
    public void updataSong(){
        Song song = new Song();
        song.setId(113);
        song.setIntroduction("Test");
        songService.updateSong(song);
    }
    @Test
    public void deleteSong(){
        songService.deleteSong(113);
    }
    @Test
    public void getAllSong(){
        Song[] songs = songService.getAllSong().toArray(new Song[0]);
        System.out.println(songs[0]);
    }
    @Test
    public void getSongBySingIdTest(){
        List<Song> list = songService.getSongBySingerId(1);
        System.out.println(list.size());
    }
    @Test
    public void getSongByIdTest(){
        System.out.println(songService.getSongById(1));
    }
    @Test
    public void getSongByNameTest(){
        System.out.println(songService.getSongByName("毛不").size());
    }

}
