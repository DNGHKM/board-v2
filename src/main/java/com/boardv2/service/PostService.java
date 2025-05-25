package com.boardv2.service;

import com.boardv2.dto.*;
import com.boardv2.entity.*;
import com.boardv2.exception.CategoryNotFoundException;
import com.boardv2.exception.DeletedPostException;
import com.boardv2.exception.PostNotFoundException;
import com.boardv2.mapper.PostMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
@AllArgsConstructor
public class PostService {
    private final PostMapper postMapper;
    private final BoardService boardService;
    private final PostFileService postFileService;
    private final CommentService commentService;
    private final CategoryService categoryService;

    public Post getPostById(Long id) {
        Post post = postMapper.findById(id);
        if (post == null) {
            throw new PostNotFoundException(id);
        }
        return post;
    }

    /**
     * 주어진 검색 조건을 기반으로 게시글 목록과 페이징 정보를 반환합니다.
     *
     * <p>이 메서드는 다음과 같은 단계를 거칩니다:</p>
     * <ol>
     *     <li>boardEngName으로 게시판 정보를 조회</li>
     *     <li>요청된 페이지 번호에 따라 offset 계산</li>
     *     <li>검색 조건 객체를 생성</li>
     *     <li>검색 조건에 맞는 전체 게시글 개수를 조회</li>
     *     <li>검색 조건에 맞는 게시글 목록을 조회</li>
     *     <li>총 페이지 수를 계산</li>
     *     <li>위 정보를 바탕으로 응답 DTO를 구성</li>
     * </ol>
     *
     * @param dto 검색 및 페이징 요청 정보를 담은 DTO
     * @return 게시글 목록, 전체 개수, 총 페이지 수를 포함하는 응답 DTO
     */

    //TODO Total 카운트 먼저 찾고~
    public ListResponseDTO getPostList(SearchRequestDTO dto) {
        // 1. boardEngName → board 변환
        Board board = boardService.getBoardByEngName(dto.getBoardEngName());

        // 2. 페이징 계산
        int offset = (dto.getPage() - 1) * dto.getSize();

        // 3. 검색 조건 생성
        SearchCondition condition = SearchCondition.from(dto, board, offset);

        // 4. 게시글 목록 조회
        List<ItemResponseDTO> posts = postMapper.findByCondition(condition);

        // 5. 전체 개수 조회
        long totalCount = postMapper.countByCondition(condition);

        // 6. 전체 페이지 수 계산
        int totalPages = (int) Math.ceil((double) totalCount / dto.getSize());

        // 7. 응답 DTO 구성
        return ListResponseDTO.from(dto, board.getEngName(), totalPages, totalCount, posts);
    }


    /**
     * 게시글 ID를 기반으로 조회 화면에 필요한 정보를 반환합니다.
     *
     * <p>해당 게시글에 대한 다음 정보를 조회하여 DTO로 변환합니다:</p>
     * <ol>
     *     <li>게시글 기본 정보</li>
     *     <li>카테고리 이름</li>
     *     <li>첨부파일 목록</li>
     *     <li>댓글 목록</li>
     * </ol>
     *
     * @param id 조회할 게시글의 ID
     * @return PostViewResponseDTO 게시글 상세 화면에 필요한 정보가 담긴 DTO
     * @throws PostNotFoundException     게시글이 존재하지 않을 경우
     * @throws CategoryNotFoundException 게시글에 연결된 카테고리가 없을 경우
     */
    public ViewResponseDTO getPostViewById(Long id) throws CategoryNotFoundException {
        // 1. 게시글 조회
        Post post = getPostById(id);

        // 2. 게시판 조회
        Board board = boardService.getBoardById(post.getBoardId());

        // 3. 카테고리 조회
        Category category = categoryService.getCategoryById(post.getCategoryId());

        // 4. 첨부파일 조회
        List<PostFile> postFileList = postFileService.getFilesByPostId(id);

        // 5. 댓글 조회
        List<Comment> commentList = commentService.getCommentsByPostId(id);

        // 6. 응답 DTO 구성
        return ViewResponseDTO.from(post, board, category.getName(), postFileList, commentList);
    }

    /**
     * 사용자가 작성한 게시글을 저장합니다.
     *
     * <p>절차는 다음과 같습니다:</p>
     * <ol>
     *     <li>비밀번호와 비밀번호 확인 값이 일치하는지 검증</li>
     *     <li>DTO를 기반으로 게시글 엔티티 생성 후 DB에 저장</li>
     *     <li>첨부파일 경로를 확인하기 위해 게시글이 속한 게시판 정보를 조회</li>
     *     <li>첨부파일이 있는 경우 파일을 저장</li>
     * </ol>
     *
     * @param writeDTO 사용자 입력 값이 담긴 DTO (작성자, 제목, 내용, 비밀번호, 파일 등)
     * @return PostWriteResponseDTO(게시글 id, 게시판명)
     * @throws IllegalArgumentException 비밀번호와 비밀번호 확인 값이 일치하지 않는 경우
     */
    public WriteResponseDTO write(WriteRequestDTO writeDTO) {
        // 1. 비밀번호와 비밀번호 확인 일치 여부 검증
//        TODO 역할 컨트롤러? 서비스? 고민
        if (!writeDTO.getPassword().equals(writeDTO.getPasswordCheck())) {
            throw new IllegalArgumentException("비밀번호와 비밀번호 확인이 일치하지 않습니다.");
        }

        // 2. 게시판 정보 조회
        Board board = boardService.getBoardByEngName(writeDTO.getBoardEngName());

        // 3. 게시글 엔티티 생성 및 저장
        Post post = Post.fromDTO(writeDTO, board.getId());
        postMapper.insert(post);

        // 4. 첨부파일이 있을 경우 첨부파일 저장
        postFileService.uploadMultipleFile(board.getEngName(), post.getId(), writeDTO.getFiles());

        return WriteResponseDTO.from(post.getId(), board.getEngName());
    }

    /**
     * 게시글을 수정합니다.
     *
     * <p>수정 절차는 다음과 같습니다:
     * <ol>
     *     <li>해당 ID의 게시글을 조회</li>
     *     <li>비밀번호 일치 여부 검증</li>
     *     <li>제목/내용 등 게시글 정보 업데이트</li>
     *     <li>첨부파일 중 삭제된 항목 정리 (파일 시스템 + DB)</li>
     *     <li>신규 첨부파일 저장</li>
     * </ol>
     *
     * @param id        수정 대상 게시글 ID
     * @param modifyDTO 클라이언트로부터 전달된 수정 요청 데이터
     * @throws PostNotFoundException    게시글이 존재하지 않을 경우
     * @throws IllegalArgumentException 비밀번호가 일치하지 않을 경우
     */
    public void modify(Long id, ModifyRequestDTO modifyDTO) {
        // 1. 게시글 조회
        Post post = getPostById(id);

        // 2. 이미 소프트 딜리트 된 게시글의 경우 삭제 불가
        if (post.isDeleted()) {
            throw new DeletedPostException(id);
        }

        // 3. 비밀번호 검증
        post.validatePassword(modifyDTO.getPassword());

        // 4. 게시글 정보 수정
        post.update(modifyDTO);
        postMapper.update(post);

        // 5. 삭제된 첨부파일 정리
        postFileService.removeDeletedFiles(post.getId(), modifyDTO);

        // 6. 추가된 첨부파일 등록
        if (!CollectionUtils.isEmpty(modifyDTO.getFiles())) {
            postFileService.uploadMultipleFile(modifyDTO.getBoardEngName(), post.getId(), modifyDTO.getFiles());
        }
    }

    /**
     * 게시글을 삭제합니다.
     *
     * <p>입력한 비밀번호가 게시글의 저장된 비밀번호와 일치할 경우 삭제가 진행됩니다.
     * 첨부파일이 삭제되며, 이후 삭제 방식은 게시글에 댓글이 있는지 여부에 따라 달라집니다:</p>
     *
     * <ul>
     *     <li>댓글이 있는 경우 → 게시글의 제목, 내용만 삭제된 상태로 변경됩니다.</li>
     *     <li>댓글이 없는 경우 → 게시글 및 관련 파일이 삭제됩니다.</li>
     * </ul>
     *
     * @param id        게시글 ID
     * @param deleteDTO 사용자 입력 DTO(비밀번호)
     * @throws PostNotFoundException    게시글이 존재하지 않는 경우
     * @throws IllegalArgumentException 비밀번호가 일치하지 않는 경우
     */
    public void delete(Long id, DeleteRequestDTO deleteDTO) {
        // 1. 게시글 조회
        Post post = getPostById(id);

        // 2. 비밀번호 검증
        post.validatePassword(deleteDTO.getPassword());

        // 3. 이미 소프트 딜리트 된 게시글의 경우 삭제 불가
        if (post.isDeleted()) {
            throw new DeletedPostException(id);
        }

        // 4. 파일삭제
        postFileService.deleteByPostId(post.getId());

        // 5-1. 댓글 있는 경우 제목, 내용만 삭제(소프트 딜리트)
        if (postHasComment(post.getId())) {
            //TODO 삭제 시 문구 외부에서?
            postMapper.softDelete(post.getId());
            return;
        }

        // 5-2. 댓글 없는 경우 완전 삭제
        //TODO 메서드명?
        postMapper.delete(post.getId());
    }

    /**
     * 게시글 조회수 증가
     *
     * @param id : 게시글 id
     */
    public void increaseViewCount(Long id) {
        postMapper.increaseViewCount(id);
    }

    /**
     * 게시글에 등록된 댓글이 존재하는지 여부를 확인합니다.
     *
     * @param postId 게시글 ID
     * @return 댓글이 하나 이상 존재하면 {@code true}, 없으면 {@code false}
     */
    private boolean postHasComment(Long postId) {
        return !commentService.getCommentsByPostId(postId).isEmpty();
    }


}
