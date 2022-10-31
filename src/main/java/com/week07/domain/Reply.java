package com.week07.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.week07.dto.request.ReplyReqDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Reply {

    @Id
    @Column(name = "replyId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;

//    @Column
    private String replyComment;

    @ManyToOne
    @JoinColumn(name = "memberId")
    @JsonIgnore
    private Member member;

    @ManyToOne
    @JoinColumn(name = "commentId")
    @JsonIgnore
    private Comment comment;


    public Reply(ReplyReqDto replyDto, Comment comment, Member member) {
        this.replyComment = replyDto.getReplyComment();
        this.member = member;
        this.comment = comment;
    }
}
