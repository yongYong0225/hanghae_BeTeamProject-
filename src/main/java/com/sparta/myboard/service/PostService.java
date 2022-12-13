package com.sparta.myboard.service;

import com.sparta.myboard.dto.PostDeleteResponseDto;
import com.sparta.myboard.dto.PostRequestDto;
import com.sparta.myboard.dto.PostResponseDto;
import com.sparta.myboard.entity.Post;
import com.sparta.myboard.entity.User;
import com.sparta.myboard.entity.UserRoleEnum;
import com.sparta.myboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

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
    public PostResponseDto creatPost(PostRequestDto requestDto, User user) {
        //토큰 검사

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
    public PostResponseDto updatePost(Long id, PostRequestDto requestDto, User user) {

        UserRoleEnum userRoleEnum = user.getRole();

        if (userRoleEnum == UserRoleEnum.ADMIN) {
            Post post = postRepository.findById(id).get();
            post.update(requestDto, user.getUsername());
            return new PostResponseDto(post);
        } else if (postRepository.existsByIdAndUsername(id, user.getUsername()) && userRoleEnum == UserRoleEnum.USER) {
            Post post = postRepository.findById(id).get();
            post.update(requestDto, user.getUsername());
            return new PostResponseDto(post);
        } else {
            throw new IllegalArgumentException("게시글을 삭제할 수 없습니다.");
        }
    }

    @Transactional
    public PostDeleteResponseDto deletePost(Long id, User user) {

        UserRoleEnum userRoleEnum = user.getRole();

        //유효한 토큰일 경우 삭제
        if (user.getRole().equals(UserRoleEnum.ADMIN)) {
            postRepository.deleteById(id);
            return new PostDeleteResponseDto("게시글 삭제 성공", HttpStatus.OK.value());
        } else if (postRepository.existsByIdAndUsername(id, user.getUsername()) && userRoleEnum == UserRoleEnum.USER){
            postRepository.deleteById(id);
            return new PostDeleteResponseDto("게시글 삭제 성공", HttpStatus.OK.value());
        } else {
            throw new IllegalArgumentException("게시글 삭제 실패");
            //새로운 출발...
        }
    }
}
