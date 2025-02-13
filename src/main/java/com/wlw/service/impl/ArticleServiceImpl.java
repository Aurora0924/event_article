package com.wlw.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.wlw.mapper.ArticleMapper;
import com.wlw.pojo.Article;
import com.wlw.pojo.PageBean;
import com.wlw.service.ArticleService;
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
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleMapper articleMapper;

    /**
     * 新增文章
     *
     * @param article
     */
    @Override
    public void add(Article article) {
        //补充属性
        article.setCreateTime(LocalDateTime.now());
        article.setUpdateTime(LocalDateTime.now());
        // 从ThreadLocal中获取当前用户信息
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        article.setCreateUser(userId);
        // 调用Mapper层的添加方法，将Article对象插入数据库
        articleMapper.add(article);
    }

    /**
     * 查询文章列表
     *
     * @param pageSize      每页记录数
     * @param pageNum       页码
     * @param categoryId    文章分类ID
     * @param state         文章状态
     * @return              返回封装了文章列表的PageBean对象
     */
    @Override
    public PageBean<Article> list(Integer pageSize, Integer pageNum, Integer categoryId, String state) {
        //1.创建PageBean对象，用于封装分页数据
        PageBean<Article> pageBean = new PageBean<>();

        //2.开启分页查询
        PageHelper.startPage(pageNum, pageSize);
        //3.调用mapper
            // ThreadLocal中获取当前用户信息
        Map<String, Object> map = ThreadLocalUtil.get();
        Integer userId = (Integer) map.get("id");
        List<Article> articleList = articleMapper.list(userId,categoryId, state);
        Page<Article> page = (Page<Article>) articleList;
        pageBean.setTotal(page.getTotal());
        pageBean.setItems(page.getResult());
        return pageBean;
    }

    /**
     * 根据ID查询文章
     *
     * @param id    文章ID
     * @return      返回查询到的文章对象
     */
    @Override
    public Article findArticleById(Integer id) {
        return articleMapper.findArticleById(id);
    }

    /**
     * 更新文章信息
     *
     * @param article   待更新的文章对象
     */
    @Override
    public void update(Article article) {
       article.setUpdateTime(LocalDateTime.now());
        articleMapper.update(article);
    }

    /**
     * 删除文章
     *
     * @param id    文章ID
     */
    @Override
    public void delete(Integer id) {
        articleMapper.delete(id);
    }
}
