package com.boardv2.service;

import com.boardv2.dto.ModifyRequestDTO;
import com.boardv2.entity.PostFile;
import com.boardv2.mapper.PostFileMapper;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class PostFileService {
    private final FileService fileService;
    private final PostFileMapper postFileMapper;

    public List<PostFile> getFilesByPostId(Long id) {
        return postFileMapper.findByPostId(id);
    }

    /**
     * 게시글에 첨부된 파일들을 저장
     *
     * <p>각 파일은 지정된 게시판(boardName)을 기준으로 하위 디렉토리에 저장되며,
     * 파일 메타데이터(PostFile)는 DB에 함께 저장</p>
     *
     * @param boardName 게시판 이름 (파일 저장 시 사용되는 하위 디렉토리 경로)
     * @param postId    게시글 ID
     * @param files     업로드된 첨부 파일 목록 (MultipartFile List)
     */
    public void uploadMultipleFile(String boardName, Long postId, List<MultipartFile> files) {
        List<PostFile> postFiles = new ArrayList<>();
        for (MultipartFile file : files) {
            if (file.isEmpty()) {
                continue;
            }
            String savedFilename = fileService.uploadFile(file, boardName);
            postFiles.add(PostFile.of(postId, boardName, savedFilename, file.getOriginalFilename()));
        }

        if (!postFiles.isEmpty()) {
            postFileMapper.insertFiles(postFiles);
        }
    }


    /**
     * 게시글 첨부파일 삭제
     *
     * <p>게시글 id를 기준으로 조회한 파일들을 삭제하고, DB에서도 정보를 삭제</p>
     *
     * @param id : 첨부파일 id
     */
    public void deleteByPostId(Long id) {
        List<PostFile> postFiles = postFileMapper.findByPostId(id);
        postFiles.forEach(pf -> fileService.deleteFile(pf.getPath(), pf.getSavedFilename()));
        postFiles.forEach(pf -> postFileMapper.deleteById(pf.getId()));
    }

    /**
     * 게시글 수정 시, 기존 첨부파일 중 클라이언트가 유지하지 않은 파일들을
     * 실제 파일 시스템과 DB에서 삭제합니다.
     *
     * <p>파일 삭제 도중 오류가 발생해도, 나머지 파일 삭제는 계속 진행됩니다.</p>
     *
     * @param postId    수정 대상 게시글의 ID
     * @param modifyDTO 클라이언트로부터 전달받은 수정 요청 DTO
     */
    public void removeDeletedFiles(Long postId, ModifyRequestDTO modifyDTO) {
        List<String> keepFilenames = modifyDTO.getPreserveFilenames();
        List<PostFile> postFiles = getFilesByPostId(postId);

        for (PostFile postFile : postFiles) {
            boolean shouldKeep = keepFilenames != null && keepFilenames.contains(postFile.getSavedFilename());
            if (shouldKeep) {
                continue;
            }

            // 파일 삭제
            fileService.deleteFile(postFile.getPath(), postFile.getSavedFilename());
            postFileMapper.deleteById(postFile.getId());
        }
    }

    public ResponseEntity<Resource> download(String savedFilename) {
        PostFile postFile = postFileMapper.findBySavedFilename(savedFilename);
        return fileService.downloadFile(postFile.getPath(), postFile.getSavedFilename(), postFile.getOriginalFilename());
    }
}