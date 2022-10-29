package com.week07.week07.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.week07.week07.dto.request.request.CommentReqDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    @Column(nullable = true)
    private String comment;


    @ManyToOne
    @JoinColumn(name = "memberId")
    @JsonIgnore
    private Member member;

    public Comment(CommentReqDto commentReqDto, Member member) {
        this.comment = commentReqDto.getComment();
        this.member = member;
    }
}
