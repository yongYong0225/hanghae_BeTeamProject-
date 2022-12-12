package com.sparta.myboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginResponseDto {
    private String msg = "로그인 완료";
    private int statusCode = 200;
}
