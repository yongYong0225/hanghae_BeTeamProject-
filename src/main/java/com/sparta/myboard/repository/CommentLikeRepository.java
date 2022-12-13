package com.sparta.myboard.repository;

import com.sparta.myboard.entity.CommentLike;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByComment_IdAndUser_Id(Long commentid, Long userid);

    void deleteByComment_IdAndUser_Id(long commnetid, Long userid);

    int countAllByComment_Id(Long commnetid);
}
