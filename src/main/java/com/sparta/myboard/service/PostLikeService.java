package com.sparta.myboard.service;


import com.sparta.myboard.dto.MsgResponseDto;
import com.sparta.myboard.entity.Post;
import com.sparta.myboard.entity.PostLike;
import com.sparta.myboard.entity.User;
import com.sparta.myboard.repository.PostLikeRepository;
import com.sparta.myboard.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostLikeService {

    private final PostLikeRepository postLikeRepository;
    private final PostRepository postRepository;

    @Transactional(readOnly = true)
    public boolean checkPostLike(Long postId, User user) {
        Optional<PostLike> postLike = postLikeRepository.findByPostIdAndUserId(postId, user.getId());
        return postLike.isEmpty();
    }

    @Transactional
    public MsgResponseDto savePostLike(Long postId, User user){
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new NullPointerException("게시글이 존재하지 않습니다.")
        );
        //커밋테스트
        if(checkPostLike(postId, user)) {
            postLikeRepository.saveAndFlush(new PostLike(post, user));
            post.updateLikeCount(1);
            postRepository.save(post);
            return new MsgResponseDto("좋아요 완료", HttpStatus.OK.value());
        } else {
            postLikeRepository.deleteByPostIdAndUserId(postId, user.getId());
            post.updateLikeCount(-1);
            return new MsgResponseDto("좋아요 취소", HttpStatus.OK.value());
        }

    }

}
