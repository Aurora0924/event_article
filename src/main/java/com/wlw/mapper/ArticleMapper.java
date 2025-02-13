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
    @Insert("insert into article(title,content,cover_img,category_id,create_user,create_time,update_time) values(#{title},#{content},#{coverImg},#{categoryId},#{createUser},now(),now())")
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
    @Select("select * from article where id=#{id}")
    Article findArticleById(Integer id);

    /**
     * 修改文章
     * @param article
     */
    @Update("update article set title=#{title},content=#{content},cover_img=#{coverImg},state=#{state},category_id=#{categoryId},update_time=now() where id=#{id}")
    void update(Article article);

    /**
     * 删除文章
     * @param id
     */
    @Delete("delete from article where id=#{id}")
    void delete(Integer id);
}
