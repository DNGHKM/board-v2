package com.boardv2.dto;

import com.boardv2.entity.Board;
import com.boardv2.entity.Comment;
import com.boardv2.entity.Post;
import com.boardv2.entity.PostFile;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;


@Getter
@Builder
public class ViewResponseDTO {

    private Long id;
    private Long boardId;
    private String boardEngName;
    private String subject;
    private String content;
    private String writer;
    private String categoryName;
    private int views;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private boolean deleted;

    private List<FileResponseDTO> files;
    private List<CommentResponseDTO> comments;

    public static ViewResponseDTO from(Post post, Board board, String categoryName, List<PostFile> files, List<Comment> comments) {
        return ViewResponseDTO.builder()
                .id(post.getId())
                .boardId(post.getBoardId())
                .boardEngName(board.getEngName())
                .subject(post.getSubject())
                .content(post.getContent())
                .writer(post.getWriter())
                .categoryName(categoryName)
                .views(post.getViews())
                .createAt(post.getCreateAt())
                .updateAt(post.getUpdateAt())
                .deleted(post.isDeleted())
                .files(files.stream()
                        .map(f -> new FileResponseDTO(f.getId(), f.getOriginalFilename(), f.getSavedFilename()))
                        .toList())
                .comments(comments.stream()
                        .map(c -> new CommentResponseDTO(c.getId(), c.getContent(), c.getCreateAt()))
                        .toList())
                .build();
    }
}
