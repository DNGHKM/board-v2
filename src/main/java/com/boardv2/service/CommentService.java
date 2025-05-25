package com.boardv2.service;

import com.boardv2.dto.CommentWriteRequestDTO;
import com.boardv2.entity.Comment;
import com.boardv2.mapper.CommentMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {
    private final CommentMapper commentMapper;

    /**
     * 댓글 조회
     *
     * <p>게시글 id를 기준으로 댓글을 조회해 반환합니다.</p>
     *
     * @param postId 게시글 id
     * @return 댓글 List
     */
    public List<Comment> getCommentsByPostId(Long postId) {
        return commentMapper.findByPostId(postId);
    }

    /**
     * 댓글 작성
     *
     * <p>DTO로 Comment 엔티티를 생성, DB에 기록한 후
     * 댓글 id를 반환합니다.</p>
     *
     * @param commentWriteDTO 사용자 입력 댓글 DTO
     * @return 댓글 id
     */
    public Long write(Long postId, CommentWriteRequestDTO commentWriteDTO) {
        Comment comment = Comment.fromDTO(postId, commentWriteDTO);
        commentMapper.insert(comment);
        return comment.getId();
    }

    /**
     * 댓글 삭제
     * <p>댓글 id를 기준으로 댓글을 삭제합니다.</p>
     * @param commentId 댓글 id
     */
    public void deleteById(Long commentId) {
        commentMapper.delete(commentId);
    }
}
