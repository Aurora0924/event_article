package com.wlw.pojo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 统一响应结果
 * @author zsw
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result<T> {
    /**
     * 业务状态码  0-成功  1-失败
     */
    private Integer code;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 快速返回操作成功响应结果(带响应数据)
     *
     * @param data 响应数据
     * @param <E>  响应数据的类型
     * @return 操作成功的响应结果
     */
    public static <E> Result<E> success(E data) {
        return new Result<>(0, "操作成功", data);
    }

    /**
     * 快速返回操作成功响应结果
     *
     * @return 操作成功的响应结果
     */
    public static Result success() {
        return new Result(0, "操作成功", null);
    }

    /**
     * 返回操作失败的响应结果
     *
     * @param message 提示信息
     * @return 操作失败的响应结果
     */
    public static Result error(String message) {
        return new Result(1, message, null);
    }
}

