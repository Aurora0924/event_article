package com.wlw.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.wlw.anno.State;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.groups.Default;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

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
    @NotNull(groups = {Update.class})
    private Integer id;
    /**
     * 文章标题
     */
    @NotEmpty
    @Pattern(regexp = "^\\S{1,10}$")
    private String title;
    /**
     * 文章内容
     */
    @NotEmpty
    private String content;
    /**
     * 文章封面
     */
    @NotEmpty
    @URL
    private String coverImg;
    /**
     * 文章状态
     */
    @State
    private String state;
    /**
     * 文章分类ID
     */
    @NotNull(groups = {Update.class})
    private Integer categoryId;
    /**
     * 创建人ID
     */
    private Integer createUser;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 添加add分组
     */
    public interface Add extends Default {}

    /**
     * 更新update分组
     */
    public interface Update extends Default{}
}