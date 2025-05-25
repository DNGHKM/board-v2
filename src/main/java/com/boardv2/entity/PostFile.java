package com.boardv2.entity;

import lombok.*;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class PostFile {
    private Long id;
    private Long postId;
    private String path;
    private String savedFilename;
    private String originalFilename;

    public static PostFile of(Long postId, String path, String savedFilename, String originalFilename) {
        return PostFile.builder()
                .postId(postId)
                .path(path)
                .savedFilename(savedFilename)
                .originalFilename(originalFilename)
                .build();
    }
}
