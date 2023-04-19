package top.jezer.controller.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectSongDTO {
    // collect的Id
    private Integer collectId;
    // 歌曲的Id
    private Integer id;
    private Integer singerId;
    private String name;
    private String introduction;
    private String pic;
    private String lyric;
    private String url;
}
