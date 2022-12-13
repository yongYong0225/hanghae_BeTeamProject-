package com.sparta.myboard.dto;


import com.sparta.myboard.entity.Post;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class PostResponseDto {
    private Long id;

    private String title;
    private String username;
    private String content;
    private int likeCount;
    private boolean likeCheck;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public PostResponseDto(Post entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.username = entity.getUsername();
        this.content = entity.getContent();
        this.createdAt = entity.getCreatedAt();
        this.modifiedAt = entity.getModifiedAt();
    }

    public PostResponseDto(Post entity, boolean likeCheck) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.username = entity.getUsername();
        this.content = entity.getContent();
        this.likeCount = entity.getLikeCount();
        this.likeCheck = likeCheck;
        this.createdAt = entity.getCreatedAt();
        this.modifiedAt = entity.getModifiedAt();

    }

}