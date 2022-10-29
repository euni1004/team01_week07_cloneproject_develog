package com.week07.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.week07.dto.request.CommentReqDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = true)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "memberId")
    @JsonIgnore
    private Member member;

    @ManyToOne
    @JoinColumn(name = "postId")
    @JsonIgnore
    private Post post;


    public Comment(CommentReqDto commentReqDto, Member member) {
        this.comment = commentReqDto.getComment();
        this.member = member;
    }
}
