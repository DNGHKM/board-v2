package com.boardv2.entity;

import lombok.*;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Board {
    private Long id;
    private String engName;
    private String korName;
}
