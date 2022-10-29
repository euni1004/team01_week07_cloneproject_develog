package com.week07.week07.domain;


import com.week07.week07.dto.request.PostReqDto;
import com.week07.week07.dto.request.tagListReqDto;
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
    private String imgUrl;


    private String content;

    @ManyToOne
    @JoinColumn(name = "account_id")
    private Member member;

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Comment> comment = new ArrayList<>();

    @OneToMany(mappedBy = "post", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Tag> tag = new ArrayList<>();

    private int likeCnt;





    public Post(PostReqDto postReqDto){
        this.title = postReqDto.getTitle();
        this.imgUrl = postReqDto.getImgUrl();
        this.content = postReqDto.getContent();

    }

    public void update(PostReqDto postReqDto){
        this.title = postReqDto.getTitle();
        this.imgUrl = postReqDto.getImgUrl();
        this.content = postReqDto.getContent();

    }
    public Post(PostReqDto postReqDto,Member member){
        this.title = postReqDto.getTitle();
        this.member = member;
        this.content = postReqDto.getContent();
    }

}
