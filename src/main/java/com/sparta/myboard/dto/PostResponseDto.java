package com.sparta.myboard.dto;


import com.sparta.myboard.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
public class PostResponseDto {
    private Long id;

    private String title;
    private String username;
    private String content;
    private int likeCount;
//    private boolean likeCheck;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PostResponseDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.username = entity.getUsername();
        this.content = entity.getContent();
        this.likeCount = entity.getLikeCount();
        this.createdAt = entity.getCreatedAt();
        this.modifiedAt = entity.getModifiedAt();
    }


    private List<CommentResponseDto> commentList = new ArrayList<>();

    public PostResponseDto(Post post, List<CommentResponseDto> commentList) { // 좋아요 체크 넣으시면 됩니다:)
        this.id = post.getId();
        this.title = post.getTitle();
        this.username = post.getUsername();
        this.content = post.getContent();
        this.likeCount = post.getLikeCount();
        this.createdAt = post.getCreatedAt();
        this.modifiedAt = post.getModifiedAt();
        this.commentList = commentList;
    }
}