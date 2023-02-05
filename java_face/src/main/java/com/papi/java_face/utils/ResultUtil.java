package com.papi.java_face.utils;

import lombok.Data;

/**
 * @author w
 * @date 2022/12/19 11:37
 * @desciption:
 */
@Data
public class ResultUtil<T> {
    /** 状态码 */
    private Integer code;

    /** 提示信息 */
    private String message;

    /** 响应数据 */
    private T data;

    /**
     * 成功,只能是0
     */
    private static final int SUCCESS_CODE = 200;
    public static final String SUCCESS_MESSAGE = "操作成功";
    /**
     * 失败
     */
    public static final int FAIL_CODE = -1;
    public static final String FAIL_MESSAGE = "操作失败";


    public static <T> ResultUtil<T> success(T data) {
        return new ResultUtil<>(SUCCESS_CODE, SUCCESS_MESSAGE, data);
    }

    public static <T> ResultUtil<T> success(String message, T data) {
        return new ResultUtil<>(SUCCESS_CODE, message, data);
    }

    public static <T> ResultUtil<T> fail() {
        return new ResultUtil<>(FAIL_CODE, FAIL_MESSAGE, null);
    }

    public static <T> ResultUtil<T> fail(String message) {
        return new ResultUtil<>(FAIL_CODE, message, null);
    }

    public static <T> ResultUtil<T> fail(int code, String message) {
        return new ResultUtil<>(code, message, null);
    }

    private ResultUtil(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

}
