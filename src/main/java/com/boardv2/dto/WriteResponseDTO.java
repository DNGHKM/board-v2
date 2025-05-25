package com.boardv2.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class WriteResponseDTO {
    private Long postId;
    private String boardEngName;

    public static WriteResponseDTO from(Long postId, String boardEngName) {
        return WriteResponseDTO.builder()
                .postId(postId)
                .boardEngName(boardEngName)
                .build();
    }
}
