package com.boardv2.exception;

public class CategoryNotFoundException extends NotFoundException {
    public CategoryNotFoundException() {
        super("해당 카테고리를 찾을 수 없습니다.");
    }
}
