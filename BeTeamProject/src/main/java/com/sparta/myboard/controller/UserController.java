package com.sparta.myboard.controller;

import com.sparta.myboard.dto.LoginRequestDto;
import com.sparta.myboard.dto.LoginResponseDto;
import com.sparta.myboard.dto.SignupRequestDto;
import com.sparta.myboard.dto.SignupResponseDto;
import com.sparta.myboard.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController //Json형태로 객체 데이터를 반환하는데 사용됨
@RequestMapping("/api/user")
@RequiredArgsConstructor //생성자를 만들어야 하는 번거로움을 없애줌
public class UserController {

    private final UserService userService; //의존성주입

    @PostMapping("/signup")
    public SignupResponseDto signup(@RequestBody/*http요청을 자바객체로*/ @Valid SignupRequestDto signupRequestDto) {
        return userService.signup(signupRequestDto); //userservice의 signup 메소드 실행(매게값 signupRequestDto)
    }

    @PostMapping("/login")
    public LoginResponseDto login(@RequestBody LoginRequestDto loginRequestDto, HttpServletResponse response) {
        return userService.login(loginRequestDto, response);
    }
}
