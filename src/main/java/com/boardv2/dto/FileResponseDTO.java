package com.boardv2.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FileResponseDTO {
    private Long id;
    private String originalFilename;
    private String savedFilename;
}
