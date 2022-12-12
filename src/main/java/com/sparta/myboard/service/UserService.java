package com.sparta.myboard.service;

import com.sparta.myboard.dto.LoginRequestDto;
import com.sparta.myboard.dto.LoginResponseDto;
import com.sparta.myboard.dto.SignupRequestDto;
import com.sparta.myboard.dto.SignupResponseDto;
import com.sparta.myboard.entity.User;
import com.sparta.myboard.jwt.JwtUtil;
import com.sparta.myboard.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @Transactional
    public SignupResponseDto signup(SignupRequestDto signupRequestDto){
        //받아온 유저네임과 패스워드를 변수에 저장
        String username = signupRequestDto.getUsername();
        String password = signupRequestDto.getPassword();

        //회원 중복 확인, 받아온 값이 유저레포지토리에 있는지 확인
        Optional<User> found = userRepository.findByUsername(username);
        if (found.isPresent()) { //존재하는 것을 찾았다면 에러처리
            throw new IllegalArgumentException("이미 등록된 ID 입니다.");
        }
        //user 객체에 두 값을 저장
        User user = new User(username, password);
        userRepository.save(user);

        return new SignupResponseDto("회원가입 성공", HttpStatus.OK.value());
    }

    @Transactional
    public LoginResponseDto login(LoginRequestDto loginRequestDto, HttpServletResponse response){
        String username = loginRequestDto.getUsername();
        String password = loginRequestDto.getPassword();

        //사용자 확인
        User user = userRepository.findByUsername(username).orElseThrow(
                () -> new IllegalArgumentException("등록된 사용자가 없습니다.")
        );

        //비밀번호 확인
        if(!user.getPassword().equals(password)){
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        response.addHeader(JwtUtil.AUTHORIZATION_HEADER, jwtUtil.createToken(user.getUsername(), user.getRole()));

        return new LoginResponseDto("로그인 성공", HttpStatus.OK.value());
    }

}
