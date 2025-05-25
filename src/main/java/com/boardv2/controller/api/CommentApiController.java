package com.boardv2.controller.api;

import com.boardv2.dto.CommentWriteRequestDTO;
import com.boardv2.service.CommentService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/comments")
@AllArgsConstructor
@Slf4j
public class CommentApiController {
    private final CommentService commentService;

    @PostMapping("/{postId}")
    public ResponseEntity<ApiResponse<Long>> writeComment(@PathVariable Long postId,
                                                       @RequestBody CommentWriteRequestDTO commentWriteDTO) {
        Long commentId = commentService.write(postId, commentWriteDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("댓글을 등록하였습니다.", commentId));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponse<Void>> deleteComment(@PathVariable Long commentId) {
        commentService.deleteById(commentId);
        return ResponseEntity.ok(ApiResponse.success("댓글을 삭제하였습니다."));
    }
}
