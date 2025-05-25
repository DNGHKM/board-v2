package com.boardv2.exception;

public class BoardNotFoundException extends NotFoundException {
    public BoardNotFoundException() {
        super("해당 게시판을 찾을 수 없습니다.");
    }
}
