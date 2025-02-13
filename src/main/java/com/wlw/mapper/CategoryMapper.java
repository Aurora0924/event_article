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
    @Insert("insert into category(category_name,category_alias,create_user,create_time,update_time) values(#{categoryName},#{categoryAlias},#{createUser},now(),now())")
    void add(Category category);

    /**
     * 查询分类
     *
     * @param userId
     * @return
     */
    @Select("select * from category where create_user=#{userId}")
    List<Category> list(Integer userId);

    /**
     * 根据id查询分类
     *
     * @param id
     * @return
     */
    @Select("select * from category where id=#{id}")
    Category findById(Integer id);

    /**
     * 修改分类
     *
     * @param category
     * @return
     */
    @Update("update category set category_name=#{categoryName},category_alias=#{categoryAlias},update_time=now() where id=#{id}")
    void update(Category category);

    /**
     * 删除分类
     * @param id
     */
    @Delete("delete from category where id=#{id}")
    void delete(Integer id);
}
