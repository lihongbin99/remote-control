package io.lihongbin.model.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {
    private static final long serialVersionUID=1L;

    private Integer code;

    private String message;

    private T data;

    public Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static Result<Object> success() {
        return new Result<>(200, "success", null);
    }
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "success", data);
    }

}
