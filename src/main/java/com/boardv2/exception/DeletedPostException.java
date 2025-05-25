package com.boardv2.exception;

public class DeletedPostException extends RuntimeException {
    public DeletedPostException(Long id) {
        super("이미 삭제된 게시글입니다. (id: " + id + ")");
    }
}