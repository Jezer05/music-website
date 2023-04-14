package top.jezer.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CollectDTO {
    // collect的Id
    private Integer id;
    private String pic;
    // 收藏的歌曲/歌单名字
    private String name;
}
