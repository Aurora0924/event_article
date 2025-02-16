package com.wlw.controller;

import com.wlw.pojo.Category;
import com.wlw.pojo.Result;
import com.wlw.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zsw
 */
@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * 处理删除请求的映射方法
     * 该方法通过HTTP DELETE请求接收一个整数参数id，表示要删除的类别ID
     * 它调用categoryService的delete方法来执行实际的删除操作
     * 成功删除后，返回一个表示成功的Result对象
     *
     * @param id 要删除的类别ID
     * @return 表示操作结果的Result对象，此处特指删除成功
     */
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Integer id) {
        categoryService.delete(id);
        return Result.success();
    }
    /**
     * 更新类别信息
     *
     * 该方法通过PUT请求接收一个经过验证的Category对象，将其传递给categoryService进行更新操作
     * 主要用途是当需要修改类别信息时，通过发送PUT请求来实现
     *
     * @param category 要更新的类别对象，通过请求体传递，并且经过验证
     * @return 返回更新操作的结果，如果成功则返回成功结果
     */
    @PutMapping("/update")
    public Result update(@RequestBody @Validated(Category.Update.class) Category category) {
        categoryService.update(category);
        return Result.success();
    }
    /**
     * 根据类别ID获取类别详细信息
     * 此方法响应GET请求，路径为/detail，返回指定类别的详细信息
     *
     * @param id 类别的唯一标识符，用于查找特定的类别对象
     * @return 返回一个Result对象，其中包含查找结果，即Category对象
     *         如果找不到指定的类别，Result对象将不包含数据
     */
    @GetMapping("/detail")
    public Result<Category> detail(Integer id) {
        return Result.success(categoryService.findById(id));
    }

    /**
     * 获取类别列表
     *
     * 通过GET请求获取所有类别信息列表
     *
     * @return 类别列表 如果没有类别信息，则返回空列表
     */
    @GetMapping("/list")
    public Result<List<Category>> list() {
        List<Category> categoryList = categoryService.list();
        return Result.success(categoryList);
    }

    /**
     * 添加分类
     *
     * 通过POST请求接收一个Category对象，将其添加到系统中
     * 此方法专注于处理添加分类的请求，具体的添加逻辑委托给categoryService
     *
     * @param category 从请求体中获取的分类对象，包含要添加的分类信息
     * @return 返回添加操作的结果，成功则返回成功结果
     */
    @PostMapping("/add")
    public Result add(@RequestBody @Validated(Category.Add.class) Category category) {
        categoryService.add(category);
        return Result.success();
    }
}
