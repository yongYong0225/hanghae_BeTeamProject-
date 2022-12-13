package com.sparta.myboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor // 모든 필드값을 파라미터로 받는 생성자 생성
@NoArgsConstructor // 파라미터가 없는 기본 생성자 생성
@Builder // 해당 클래스에 자동으로 빌더 추가
public class MsgResponseDto {
    private String msg;
    private int statusCode;
}
