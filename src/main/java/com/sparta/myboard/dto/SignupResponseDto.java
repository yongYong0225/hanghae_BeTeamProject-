package com.sparta.myboard.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class SignupResponseDto {
    private String msg = "회원가입 완료";
    private int statusCode = 200;
}
