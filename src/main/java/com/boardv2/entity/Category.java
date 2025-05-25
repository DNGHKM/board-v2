package com.boardv2.entity;

import lombok.*;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    private Long id;
    private String name;
}
