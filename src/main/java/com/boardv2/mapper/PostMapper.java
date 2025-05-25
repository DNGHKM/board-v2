package com.boardv2.mapper;

import com.boardv2.dto.ItemResponseDTO;
import com.boardv2.dto.SearchCondition;
import com.boardv2.entity.Post;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostMapper {
    Post findById(Long postId);

    void insert(Post post);

    void update(Post post);

    void softDelete(Long id);

    void delete(Long id);

    void increaseViewCount(Long id);

    List<ItemResponseDTO> findByCondition(SearchCondition cond);

    Long countByCondition(SearchCondition condition);
}
