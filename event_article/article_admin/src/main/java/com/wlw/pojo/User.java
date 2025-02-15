package com.wlw.pojo;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDateTime;

/**
 * @author zsw
 */
@Data
public class User {

    /**
     * 用户id
     */
    @NotNull
    private Integer id;
    /**
     * 用户名
     */

    private String username;
    /**
     * 密码
     */
    @JsonIgnore
    private String password;
    /**
     * 昵称
     */
    @NotNull
    @Pattern(regexp = "^\\S{1,10}$")
    private String nickname;
    /**
     * 邮箱
     */
    @NotEmpty
    @Email
    private String email;
    /**
     * 用户头像地址
     */
    private String userPic;
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
}
