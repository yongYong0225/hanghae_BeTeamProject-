package com.sparta.myboard.entity;


import com.sparta.myboard.dto.PostRequestDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity // entity로 사용
@NoArgsConstructor
public class Post extends Timestamped{

    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column (nullable = false)
    private String username;

    @Column (nullable = false)
    private String content;

    @Column (nullable = false)
    private int likeCount;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;



    public Post(PostRequestDto requestDto, String username){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.username = username;
    }

    public void update(PostRequestDto requestDto, String username){
        this.title = requestDto.getTitle();
        this.content = requestDto.getContent();
        this.username = username;
    }

    public void updateLikeCount(int likeCount){

        this.likeCount += likeCount;
    }


}
