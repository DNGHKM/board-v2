package com.boardv2.constant;

public interface ValidationConstant {
    int PW_MIN_LENGTH = 4;
    int PW_MAX_LENGTH = 16;
    String PW_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[!@#$%^&*()\\-_=+{}\\[\\]:;\"'<>,.?/]).{4,16}$";
    int WRITER_MIN_LENGTH = 3;
    int WRITER_MAX_LENGTH = 4;
    int SUBJECT_MIN_LENGTH = 4;
    int SUBJECT_MAX_LENGTH = 100;
    int CONTENT_MIN_LENGTH = 4;
    int CONTENT_MAX_LENGTH = 2000;
}
