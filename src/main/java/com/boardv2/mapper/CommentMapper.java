package com.boardv2.mapper;

import com.boardv2.entity.Comment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    void insert(Comment comment);

    void delete(Long commentId);

    List<Comment> findByPostId(Long postId);
}
