package com.boardv2.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentWriteRequestDTO {
    private String content;
}
