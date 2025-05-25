package com.boardv2.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ItemResponseDTO {
    private Long id;
    private String categoryName;
    private boolean hasFile;
    private String subject;
    private String writer;
    private int views;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
}
