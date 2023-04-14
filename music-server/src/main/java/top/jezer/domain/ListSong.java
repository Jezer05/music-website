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
public class ListSong {
    private Integer id;
    private Integer songId;
    private Integer songListId;
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    private Integer deleted;
}
