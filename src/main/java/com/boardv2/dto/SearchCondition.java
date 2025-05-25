package com.boardv2.dto;

import com.boardv2.entity.Board;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class SearchCondition {
    private Long boardId;
    private String categoryName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String keyword;
    private int size;
    private int offset;

    public static SearchCondition from(SearchRequestDTO dto, Board board, int offset) {
       return SearchCondition.builder()
                .boardId(board.getId())
                .categoryName(dto.getCategoryName())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .keyword(dto.getKeyword())
                .size(dto.getSize())
                .offset(offset)
                .build();
    }
}
