package com.boardv2.service;

import com.boardv2.entity.Category;
import com.boardv2.exception.CategoryNotFoundException;
import com.boardv2.mapper.CategoryMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {
    private final CategoryMapper categoryMapper;

    /**
     * 카테고리 List 반환
     *
     * <p>게시판에 등록된 모든 카테고리를 반환합니다.</p>
     *
     * @return 조회된 {@link Category} 객체 List
     */
    public List<Category> getAllCategories() {
        return categoryMapper.findAll();
    }

    /**
     * ID에 해당하는 카테고리를 반환합니다.
     *
     * <p>존재하지 않는 ID가 전달될 경우 {@link CategoryNotFoundException}을 발생시킵니다.</p>
     *
     * @param id 조회할 카테고리의 ID
     * @return 조회된 {@link Category} 객체
     * @throws CategoryNotFoundException ID에 해당하는 카테고리가 없을 경우
     */
    public Category getCategoryById(Long id) {
        Category category = categoryMapper.findById(id);
        if (category == null) {
            throw new CategoryNotFoundException();
        }
        return category;
    }
}
