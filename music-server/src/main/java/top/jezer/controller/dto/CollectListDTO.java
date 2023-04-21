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
