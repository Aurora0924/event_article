package com.wlw.pojo;


import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author zsw
 * 文章实体类属性说明：
 * * 包含文章的基本信息和状态，如主键ID、标题、内容等。
 */
@Data
public class Article {

    /**
     * 主键ID主键ID
     */
    private Integer id;
    /**
     * 文章标题
     */
    private String title;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 文章封面
     */
    private String coverImg;
    /**
     * 文章状态
     */
    private String state;
    /**
     * 文章分类ID
     */
    private Integer categoryId;
    /**
     * 创建人ID
     */
    private Integer createUser;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}