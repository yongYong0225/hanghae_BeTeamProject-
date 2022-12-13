package com.sparta.myboard.dto;

import com.sparta.myboard.entity.Post;
import com.sparta.myboard.entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentRequestDto {
   private String content;
}
