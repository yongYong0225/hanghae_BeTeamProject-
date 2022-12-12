package com.sparta.myboard.dto;

import lombok.Getter;

@Getter
public class PostDeleteResponseDto {
    private String msg = "게시글 삭제 성공";
    private int statusCode = 200;
}
