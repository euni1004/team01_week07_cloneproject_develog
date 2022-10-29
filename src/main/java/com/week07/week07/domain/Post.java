package com.week07.week07.domain;


import com.week07.week07.dto.request.PostReqDto;
//import com.week07.week07.dto.request.tagListReqDto;
import com.week07.week07.dto.request.PostUpdateReqDto;
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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long postId;


    private String title;
    @Lob
    private String imgUrlPath;

    private String content;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Member member;

//    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
//    private List<Comment> comment = new ArrayList<>();

//    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
//    private List<Tag> tag = new ArrayList<>();

    public Post(PostReqDto postReqDto){
        this.title = postReqDto.getPostTitle();
        this.content = postReqDto.getPostContent();
    }

    public void update(PostUpdateReqDto postUpdateReqDto){
        if(postUpdateReqDto.getPostContent()!=null){
            this.content = postUpdateReqDto.getPostContent();
        }
//        if(postUpdateReqDto.getPostTag()!=null){
//            this.ta
//        }
    }

    public Post(PostReqDto postReqDto,Member member,String path){
        this.title = postReqDto.getPostTitle();
        this.imgUrlPath = path;
        this.member = member;
        this.content = postReqDto.getPostContent();
    }
}
