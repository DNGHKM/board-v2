package com.boardv2.entity;

import com.boardv2.dto.ModifyRequestDTO;
import com.boardv2.dto.WriteRequestDTO;
import com.boardv2.util.PasswordUtil;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    private Long id;
    private Long boardId;
    private String subject;
    private String content;
    private String writer;
    private String password;
    private Long categoryId;
    private int views;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;
    private boolean deleted;

    /**
     * 사용자가 입력한 게시글 생성 DTO를 기반으로 엔티티를 생성함
     *
     * @param writeDTO : 사용자 입력 DTO
     * @param boardId : 게시판 id
     * @return : Post
     */
    public static Post fromDTO(WriteRequestDTO writeDTO, Long boardId) {
        return Post.builder()
                .boardId(boardId)
                .subject(writeDTO.getSubject())
                .content(writeDTO.getContent())
                .writer(writeDTO.getWriter())
                .password(PasswordUtil.encode(writeDTO.getPassword()))
                .categoryId(writeDTO.getCategoryId())
                .views(0)
                .createAt(LocalDateTime.now())
                .updateAt(null)
                .build();
    }

    public void update(ModifyRequestDTO modifyDTO) {
        this.writer = modifyDTO.getWriter();
        this.subject = modifyDTO.getSubject();
        this.content = modifyDTO.getContent();
        this.updateAt = LocalDateTime.now();
    }

    /**
     * 게시글의 비밀번호가 입력된 비밀번호와 일치하는지 검증합니다.
     *
     * @param inputPassword 사용자가 입력한 비밀번호
     * @throws IllegalArgumentException 비밀번호가 일치하지 않을 경우
     */
    public void validatePassword(String inputPassword) {
        if (!PasswordUtil.matches(inputPassword, this.password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
    }
}
