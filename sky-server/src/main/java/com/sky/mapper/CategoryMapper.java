package com.sky.mapper;

import com.github.pagehelper.Page;
import com.sky.dto.CategoryPageQueryDTO;
import com.sky.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper {


    /**
     * 分类管理分页查询
     * @param categoryPageQueryDTO
     * @return
     */
    Page<Category> queryPage(CategoryPageQueryDTO categoryPageQueryDTO);

    /**
     * 编辑分类信息
     * @param category
     */
    void update(Category category);
}
