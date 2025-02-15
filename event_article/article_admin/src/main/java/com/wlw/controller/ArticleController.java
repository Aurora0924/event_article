package com.wlw.controller;

import com.wlw.pojo.Article;
import com.wlw.pojo.PageBean;
import com.wlw.pojo.Result;
import com.wlw.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author zsw
 */
@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    private ArticleService articleService;

    /**
     * 删除文章
     *
     * @param id 文章ID
     * @return 删除结果
     */
    @DeleteMapping
    public Result delete(Integer id) {
        articleService.delete(id);
        return Result.success();
    }

    /**
     * 更新文章
     *
     * @param article 更新后的文章对象
     * @return 更新结果
     */
    @PutMapping
    public Result update(@RequestBody @Validated(Article.Update.class) Article article) {
        articleService.update(article);
        return Result.success();
    }

    /**
     * 获取文章详情
     *
     * @param id 文章ID
     * @return 文章详情结果
     */
    @GetMapping("/detail")
    public Result detail(Integer id) {
        return Result.success(articleService.findArticleById(id));
    }

    /**
     * 获取文章列表
     *
     * @param pageSize  每页数量
     * @param pageNum   页数
     * @param categoryId 文章分类ID，可选
     * @param state     文章状态，可选
     * @return 文章列表结果
     */
    @GetMapping
    public Result<PageBean<Article>> list(Integer pageSize, Integer pageNum, @RequestParam(required = false) Integer categoryId, @RequestParam(required = false) String state) {
        PageBean<Article> articlePageBean = articleService.list(pageSize, pageNum, categoryId, state);
        return Result.success(articlePageBean);
    }

    /**
     * 添加文章
     *
     * @param article
     * @return
     */
    @PostMapping
    public Result add(@RequestBody @Validated(Article.Add.class) Article article) {
        articleService.add(article);
        return Result.success();
    }
}
