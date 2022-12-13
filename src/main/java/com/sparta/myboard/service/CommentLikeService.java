package com.sparta.myboard.service;

import com.sparta.myboard.dto.CommentLikeResponseDto;
import com.sparta.myboard.entity.CommentLike;
import com.sparta.myboard.repository.CommentLikeRepository;
import com.sparta.myboard.repository.PostRepository;
import com.sparta.myboard.repository.UserRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Builder
@RequiredArgsConstructor
public class CommentLikeService {
    private final PostRepository postRepository;
    private final CommentRepository commentRepository;
    private final CommentLikeRepository commentLikeRepository;
    private final UserRepository userRepository;

    @Transactional
    public CommentLikeResponseDto CommentLike(User user, Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new IllegalArgumentException("댓글을 찾을 수 없습니다.")
        );

        if (commentLikeRepository.findByComment_IdAndUser_Id(commentId, user.getId()).isEmpty()){
//            comment.commentLikeUpDown(1);
            CommentLike commentLike = CommentLike.builder()
                    .comment(comment)
                    .user(user)
                    .build();
            commentLikeRepository.save(commentLike);
            return new CommentLikeResponseDto("좋아요", HttpStatus.OK.value());
        }else{
//            comment.commentLikeUpDown(-1);
            commentLikeRepository.deleteByComment_IdAndUser_Id(comment.getId(), user.getId());
            return new CommentLikeResponseDto("좋아요 취소", HttpStatus.OK.value());
        }
    }
}
