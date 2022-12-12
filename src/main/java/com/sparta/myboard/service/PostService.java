package com.sparta.myboard.service;

import com.sparta.myboard.dto.PostDeleteResponseDto;
import com.sparta.myboard.dto.PostRequestDto;
import com.sparta.myboard.dto.PostResponseDto;
import com.sparta.myboard.entity.Post;
import com.sparta.myboard.entity.User;
import com.sparta.myboard.entity.UserRoleEnum;
import com.sparta.myboard.jwt.JwtUtil;
import com.sparta.myboard.repository.PostRepository;
import com.sparta.myboard.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    //@Transactional -> acid캐시를 깨지지 않게 해줌
    @Transactional(readOnly = true)
    public List<PostResponseDto> getPosts() { //#2 메서드 실행
        return //#7 컨트롤러로 데이터를 리턴해준다
                postRepository.findAllPostByOrderByCreatedAtDesc() //#3 레포지토리가 데이터를 찾아옴
                        .stream() //#4 stream 클래스로 찾아온 데이터를 변환시켜줌
                        .map(PostResponseDto::new) //#5 PostResponseDto에 레포지토리가 찾아온 데이터를 할당시켜줌
                        .collect(Collectors.toList()); //#6 PostResponseDto를 리스트에 담아줌
    }

    @Transactional
    public PostResponseDto creatPost(PostRequestDto requestDto, HttpServletRequest httpServletRequest) {
        //토큰 검사
        User user = getUserInfo(httpServletRequest);

        Post post = new Post(requestDto, user.getUsername());
        post = postRepository.save(post);
        post.setUser(user);
        return new PostResponseDto(post);
    }

    @Transactional(readOnly = true)
    public PostResponseDto getPost(Long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new IllegalArgumentException("아이디가 존재하지 않습니다.")
        );
        return new PostResponseDto(post);
    }

    @Transactional
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, HttpServletRequest httpServletRequest) {
        User user = getUserInfo(httpServletRequest);

        if (postRepository.existsByIdAndUsername(id, user.getUsername()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
            Post post = postRepository.findById(id).get();
            post.update(requestDto, user.getUsername());
            return new PostResponseDto(post);
        } else {
            throw new IllegalArgumentException("게시글을 찾을 수 없습니다.");
        }
    }

    @Transactional
    public PostDeleteResponseDto deletePost(Long id, HttpServletRequest httpServletRequest) {
        User user = getUserInfo(httpServletRequest);

        //유효한 토큰일 경우 삭제
        if (postRepository.existsByIdAndUsername(id, user.getUsername()) || user.getRole().equals(UserRoleEnum.ADMIN)) {
            postRepository.deleteById(id);
            return new PostDeleteResponseDto("게시글 삭제 성공", HttpStatus.OK.value());
        } else {
            throw new IllegalArgumentException("게시글 삭제 실패");
        }
    }


    private User getUserInfo(HttpServletRequest httpServletRequest) {
        String token = jwtUtil.resolveToken(httpServletRequest);
        Claims claims;

        //유효한 토큰일 경우 수정 가능
        if (token != null) {
            if (jwtUtil.validateToken(token)) {
                // 토큰에서 사용자 정보 가져오기
                claims = jwtUtil.getUserInfoFromToken(token);
            } else {
                throw new IllegalArgumentException("Token Error");
            }

            // 토큰에서 가져온 사용자 정보를 사용하여 DB 조회
            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(
                    () -> new IllegalArgumentException("Token Error")
            );
            return user;
        } else {
            throw new IllegalArgumentException("Token Error");
        }
    }
}
