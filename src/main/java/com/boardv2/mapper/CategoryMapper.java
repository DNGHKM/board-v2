package com.boardv2.mapper;

import com.boardv2.entity.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> findAll();

    Category findById(Long categoryId);
}
