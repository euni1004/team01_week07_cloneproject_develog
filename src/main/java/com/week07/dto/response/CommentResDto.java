package com.week07.dto.response;

import com.week07.domain.Comment;
import com.week07.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentResDto {
    private String userName;
    private String userImgPath;
    private String comment;
    private Long commentId;

    public CommentResDto(Member member, Comment comment) {
        this.userName = member.getUserName();
        this.userImgPath = member.getUserImgPath();
        this.comment = comment.getComment();
        this.commentId = comment.getCommentId();
    }

}
