package com.boardv2.exception;

//기존 Mybatis NotFoundException은 checked exception이라서 unchecked로 새로 클래스 정의함
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) {
        super(message);
    }
}