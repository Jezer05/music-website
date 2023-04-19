package top.jezer.controller.dto;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;

import java.util.Date;

public class CollectListDTO {
    // collect的Id
    private Integer collectId;
    // 歌单数据
    private Integer id;
    private String title;
    private String pic;
    private String introduction;
    private String style;

}
