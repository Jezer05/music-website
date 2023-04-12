package top.jezer.domain;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Integer id;
    private Integer userId;
    private Integer songId;
    private Integer songListId;
    private String content;
    private Integer type;
    private Integer mark;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    private Integer deleted;
}
