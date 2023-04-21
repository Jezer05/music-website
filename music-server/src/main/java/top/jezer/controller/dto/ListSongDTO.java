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
public class ListSongDTO {
    private Integer listSongId;
    private Integer id;
    private Integer singerId;
    private String name;
    private String introduction;
    private String lyric;
    private String pic;
    private String url;
}
