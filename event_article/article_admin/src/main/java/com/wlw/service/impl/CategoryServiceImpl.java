package com.wlw.service.impl;

import com.wlw.mapper.CategoryMapper;
import com.wlw.pojo.Category;
import com.wlw.service.CategoryService;
import com.wlw.utils.ThreadLocalUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * @author zsw
 */
@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;

    /**
     * 添加类别
     * <p>
     * 此方法用于将新的类别信息添加到数据库中在执行添加操作之前，会设置类别的创建时间和更新时间为当前时间，
     * 并将创建用户设置为当前操作用户这些信息有助于跟踪类别的创建和更新情况
     *
     * @param category 要添加的类别对象，包含类别相关信息
     */
    @Override
    public void add(Category category) {
        // 设置创建时间、更新时间为当前时间，创建用户为当前操作用户
        category.setCreateTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        // 从ThreadLocal中获取当前用户信息
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        category.setCreateUser(userId);
        // 调用Mapper层的添加方法，将Category对象插入数据库
        categoryMapper.add(category);
    }

    /**
     * 获取类别列表
     * <p>
     * 此方法用于获取当前用户的所有类别列表通过从ThreadLocal中获取当前用户信息，
     * 然后调用Mapper层的查询方法来获取类别列表这种方法确保了用户只能访问自己的类别信息
     *
     * @return 当前用户的类别列表
     */
    @Override
    public List<Category> list() {
        // 从ThreadLocal中获取当前用户信息
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Category> categoryList = categoryMapper.list(userId);
        return categoryList;
    }

    @Override
    public Category findById(Integer id) {

        return categoryMapper.findById(id);
    }

    @Override
    public void update(Category category) {

        category.setUpdateTime(LocalDateTime.now());
        categoryMapper.update(category);
    }

    @Override
    public void delete(Integer id) {
        categoryMapper.delete(id);
    }


}
