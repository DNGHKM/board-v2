package com.boardv2.controller.api;

import com.boardv2.dto.*;
import com.boardv2.service.PostService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/posts")
@AllArgsConstructor
@Slf4j
public class PostApiController {
    private final PostService postService;

    @GetMapping
    public ResponseEntity<ApiResponse<ListResponseDTO>> getPostList(@ModelAttribute @Valid SearchRequestDTO listRequestDTO) {
        ListResponseDTO dto = postService.getPostList(listRequestDTO);
        return ResponseEntity.ok(ApiResponse.success("게시글을 목록을 조회하였습니다.", dto));
    }

    @GetMapping("/{postId}")
    public ResponseEntity<ApiResponse<ViewResponseDTO>> getPostView(@PathVariable Long postId) {
        ViewResponseDTO dto = postService.getPostViewById(postId);
        return ResponseEntity.ok(ApiResponse.success("게시글을 조회하였습니다.", dto));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<WriteResponseDTO>> writePost(@ModelAttribute @Valid WriteRequestDTO writeDTO) {
        WriteResponseDTO dto = postService.write(writeDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponse.success("게시글을 등록하였습니다.", dto));
    }

    //TODO PATCH ? PUT ?
    @PatchMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> modifyPost(@PathVariable Long postId,
                                                        @ModelAttribute @Valid ModifyRequestDTO modifyDTO) {
        postService.modify(postId, modifyDTO);
        return ResponseEntity.ok(ApiResponse.success("게시글을 수정하였습니다."));
    }

    @DeleteMapping("/{postId}")
    public ResponseEntity<ApiResponse<Void>> deletePost(@PathVariable Long postId,
                                                        @RequestBody @Valid DeleteRequestDTO deleteDTO) {
        postService.delete(postId, deleteDTO);
        return ResponseEntity.ok(ApiResponse.success("게시글을 삭제하였습니다."));
    }

    //TODO return 증가된 값?
    @PostMapping("/{postId}/viewCount")
    public ResponseEntity<ApiResponse<Void>> increaseViewCount(@PathVariable Long postId) {
        postService.increaseViewCount(postId);
        return ResponseEntity.ok(ApiResponse.success("조회수가 증가되었습니다."));
    }
}
