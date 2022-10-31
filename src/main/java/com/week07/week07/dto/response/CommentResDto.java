package com.week07.week07.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResDto {

    private String userName;
    private String userImg;
    private String comment;
    private Long commentId;
}
