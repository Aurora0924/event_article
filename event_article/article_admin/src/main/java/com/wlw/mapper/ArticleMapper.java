package com.wlw.mapper;

import com.wlw.anno.State;
import com.wlw.pojo.Article;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @author zsw
 */
@Mapper
public interface ArticleMapper {
    /**
     * 添加文章
     * @param article
     */
    void add(Article article);

    /**
     * 查询文章列表 分页数据
     * @param userId
     * @param categoryId
     * @param state
     * @return
     */
    List<Article> list(Integer userId, Integer categoryId, String state);

    /**
     * 根据id查询文章
     * @param id
     * @return
     */
    Article findArticleById(Integer id);

    /**
     * 修改文章
     * @param article
     */
    void update(Article article);

    /**
     * 删除文章
     * @param id
     */
    void delete(Integer id);
}
