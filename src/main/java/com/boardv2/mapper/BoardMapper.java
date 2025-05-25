package com.boardv2.mapper;

import com.boardv2.entity.Board;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BoardMapper {
    Board findById(Long boardId);

    Board findByEngName(String engName);
}
