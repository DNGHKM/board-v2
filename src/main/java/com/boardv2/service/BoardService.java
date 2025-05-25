package com.boardv2.service;

import com.boardv2.entity.Board;
import com.boardv2.exception.BoardNotFoundException;
import com.boardv2.mapper.BoardMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BoardService {
    private final BoardMapper boardMapper;

    /**
     * ID에 해당하는 게시판을 반환합니다.
     *
     * <p>존재하지 않는 ID가 전달될 경우 {@link BoardNotFoundException}을 발생시킵니다.</p>
     *
     * @param id 조회할 게시판의 ID
     * @return 조회된 {@link Board} 객체
     * @throws BoardNotFoundException ID에 해당하는 게시판이 없을 경우
     */
    public Board getBoardById(Long id) {
        Board board = boardMapper.findById(id);
        if (board == null) {
            throw new BoardNotFoundException();
        }
        return board;
    }

    /**
     * 게시판 영문이름에 해당하는 게시판을 반환합니다.
     *
     * <p>존재하지 않는 게시판명이 전달될 경우 {@link BoardNotFoundException}을 발생시킵니다.</p>
     *
     * @param engName 조회할 게시판의 영문명
     * @return 조회된 {@link Board} 객체
     * @throws BoardNotFoundException 해당 게시판 영문명에 해당하는 게시판이 없을 경우
     */
    public Board getBoardByEngName(String engName) {
        Board board = boardMapper.findByEngName(engName);
        if (board == null) {
            throw new BoardNotFoundException();
        }
        return board;
    }
}
