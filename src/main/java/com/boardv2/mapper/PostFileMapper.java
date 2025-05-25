package com.boardv2.mapper;

import com.boardv2.entity.PostFile;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface PostFileMapper {
    void insertFiles(List<PostFile> postFiles);

    List<PostFile> findByPostId(Long postId);

    PostFile findBySavedFilename(String savedFilename);

    void deleteById(Long id);
}
