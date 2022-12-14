package com.sparta.myboard.repository;

import com.sparta.myboard.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.metamodel.SingularAttribute;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    List<Post> findAllPostByOrderByCreatedAtDesc();
    // List<Article> findAllByOrderByCreatedAtDesc();

    //ID, pw에 맞는 Post로 찾아옴
    Optional<Post> findById(Long id);

    Boolean existsByIdAndUsername(Long id, String username);

}
