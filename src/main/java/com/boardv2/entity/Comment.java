package com.boardv2.entity;

import com.boardv2.dto.CommentWriteRequestDTO;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    private Long id;
    private Long postId;
    private String content;
    private LocalDateTime createAt;

    public static Comment fromDTO(Long postId, CommentWriteRequestDTO commentWriteDTO) {
        return Comment.builder()
                .postId(postId)
                .content(commentWriteDTO.getContent())
                .createAt(LocalDateTime.now())
                .build();
    }
}
