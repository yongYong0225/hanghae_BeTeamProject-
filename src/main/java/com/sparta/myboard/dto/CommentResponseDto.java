package com.sparta.myboard.dto;

import com.sparta.myboard.entity.Comment;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDto {
    private Long id;
    private String username;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CommentResponseDto (Comment comment) {
        this.id = comment.getId();
        this.username = comment.getUsername();
        this.content = comment.getContent();
        this.createdAt = comment.getCreatedAt();
        this.modifiedAt = comment.getModifiedAt();

//    public static CommentResponseDto of(Comment comment, boolean bool) {
//        return CommentResponseDto.builder()
//                .id(comment.getId())
//                .postId(comment.getUser().getId())
//                .content(comment.getContent())
//                .isWritten(bool)
//                .build();
    }

    //    public CommentResponseDto(Comment entity) {
//        this.id = entity.getId();
//        this.content = entity.getContent();
//        this.postId = entity.getPost().getId();
//    }
}

