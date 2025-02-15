package com.wlw.service;

import com.wlw.pojo.Category;

import java.util.List;

/**
 * @author zsw
 */
public interface CategoryService {
    /**
     * 添加分类
     * @param category
     */
    void add(Category category);

    /**
     * 查询所有分类
     * @return
     */
    List<Category> list();

    /**
     * 根据id查询分类
     * @return
     */
    Category findById(Integer id);

    /**
     * 修改分类
     * @param category
     */
    void update(Category category);

    /**
     * 根据id删除分类
     * @param id
     */
    void delete(Integer id);
}
