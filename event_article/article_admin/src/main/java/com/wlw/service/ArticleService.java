package com.wlw.service;

import com.wlw.pojo.Article;
import com.wlw.pojo.PageBean;

/**
 * @author zsw
 */
public interface ArticleService {
    /**
     * 新增文章
     * @param article
     */
    void add(Article article);

    /**
     * 分页查询文章
     * @param pageSize
     * @param pageNum
     * @param categoryId
     * @param state
     * @return
     */
    PageBean<Article> list(Integer pageSize, Integer pageNum, Integer categoryId, String state);

    /**
     * 查询文章详情
     * @param id
     * @return
     */
    Article findArticleById(Integer id);

    /**
     * 更新文章
     * @param article
     */
    void update(Article article);

    /**
     * 删除文章
     * @param id
     */
    void delete(Integer id);
}
