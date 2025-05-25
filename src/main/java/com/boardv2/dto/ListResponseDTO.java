package com.boardv2.dto;

import com.boardv2.entity.Board;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ListResponseDTO {
    private String boardEngName;
    private int page;
    private int size;
    private int totalPages;
    private long postCount;
    private List<ItemResponseDTO> posts;

    public static ListResponseDTO from(SearchRequestDTO dto,
                                       String boardEngName,
                                       int totalPages, long totalCount,
                                       List<ItemResponseDTO> posts) {
        return ListResponseDTO.builder()
                .boardEngName(boardEngName)
                .page(dto.getPage())
                .size(dto.getSize())
                .totalPages(totalPages)
                .postCount(totalCount)
                .posts(posts)
                .build();
    }
}