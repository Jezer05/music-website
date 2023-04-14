package top.jezer.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Integer id;
    private String username;
    private String avatar;
    private String content;
    private Integer mark;
}
