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
    private  Long tagId;

    private String tag;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "post_id")
    private Post post;




}
