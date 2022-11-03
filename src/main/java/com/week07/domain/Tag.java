package com.week07.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    private String tagName;

    @ManyToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    @JoinColumn(name = "postId")
    private Post post;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "memberId")
    private Member member;

    public Tag(String postTag, Member member,Post post) {

        this.tagName = postTag;
        this.post = post;
        this.member = member;

    }
}
