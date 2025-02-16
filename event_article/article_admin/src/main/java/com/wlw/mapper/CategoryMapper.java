package com.wlw.mapper;

import com.wlw.pojo.Category;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author zsw
 */
@Mapper
public interface CategoryMapper {

    /**
     * 添加分类
     *
     * @param category
     */
    void add(Category category);

    /**
     * 查询分类
     *
     * @param userId
     * @return
     */
    List<Category> list(Integer userId);

    /**
     * 根据id查询分类
     *
     * @param id
     * @return
     */
    Category findById(Integer id);

    /**
     * 修改分类
     *
     * @param category
     * @return
     */
    void update(Category category);

    /**
     * 删除分类
     * @param id
     */
    void delete(Integer id);
}
