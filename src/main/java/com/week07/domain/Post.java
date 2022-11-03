package com.week07.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.week07.dto.request.PostReqDto;
import com.week07.dto.request.PostUpdateReqDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Post extends Timestamped {


    @Id
    @Column(name = "postId")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;

    @Column(nullable = false)
    private String title;

    private String modifyPost;
    @ElementCollection
    private List<String> imgUrlPath;

    @ElementCollection
    private List<String> imgUrl;

    @Column(nullable = false)
    private String content;

    @ManyToOne
    @JoinColumn(name = "memberId")
    @JsonIgnore
    private Member member;

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Comment> comment = new ArrayList<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Tag> tag = new ArrayList<>();

//    @ElementCollection
//    private List<String> tag;

    public Post(PostReqDto postReqDto){
        this.title = postReqDto.getPostTitle();
        this.content = postReqDto.getPostContent();
    }

    public void update(PostUpdateReqDto postUpdateReqDto,String modifyPost){
        this.modifyPost = modifyPost;
        this.content = postUpdateReqDto.getPostContent();
    }

    public Post(PostReqDto postReqDto,Member member,List<String> path, List<String> url){
        this.title = postReqDto.getPostTitle();
        this.imgUrlPath = path;
        this.imgUrl = url;
        this.member = member;
        this.content = postReqDto.getPostContent();
    }

    public void updateTag(List<Tag> tagList) {

        this.tag = tagList;
    }
}
