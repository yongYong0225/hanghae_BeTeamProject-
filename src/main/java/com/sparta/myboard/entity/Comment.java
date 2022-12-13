package com.sparta.myboard.entity;

import com.sparta.myboard.dto.CommentRequestDto;
import com.sparta.myboard.dto.PostRequestDto;
import jdk.jshell.Snippet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Entity // entity로 사용
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String content;  // 댓글 내용

    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // 작성자

    public Comment(CommentRequestDto commentRequestDto, Post post, User user) {
        this.content = commentRequestDto.getContent();
        this.username = user.getUsername();
        this.post = post;
        this.user = user;
    }

    public void update(CommentRequestDto commentRequestDto) {
        this.content = commentRequestDto.getContent();
    }
}
