package com.week07.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.week07.dto.request.CommentReqDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private String comment;

    @ManyToOne
    @JoinColumn(name = "postId")
    @JsonIgnore
    private Post post;

    @ManyToOne
    @JoinColumn(name = "memberId")
    @JsonIgnore
    private Member member;

//    @OneToMany(mappedBy = "comment")
    @OneToMany(mappedBy = "comment", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<Reply> replies = new ArrayList<>();

    public Comment(CommentReqDto commentReqDto, Post post, Member member) {
        this.comment = commentReqDto.getComment();
        this.post = post;
        this.member = member;
    }
}
