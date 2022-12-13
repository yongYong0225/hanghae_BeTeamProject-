package com.sparta.myboard.repository;

import com.sparta.myboard.entity.Comment;
import com.sparta.myboard.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndUserId(Long cmtId, Long UserId);
}
