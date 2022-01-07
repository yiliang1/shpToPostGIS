package com.tlw.storagemanagement.utils;

public class Result<T> {
    public static final int STATUS_OK = 1;
    public static final int STATUS_FAILED = 0;
    public static final String DEFAUL_MESSAGE = "操作成功";
    private int status = 1;
    private String message = "操作成功";
    private T content;

    public Result() {
    }

    public Result(T content) {
        this.content = content;
    }

    public Result(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public Result(int status, T content) {
        this.status = status;
        this.content = content;
    }

    public T getContent() {
        return this.content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static <T> Result<T> fail(String message) {
        return new Result(0, message);
    }

    public static <T> Result<T> success(T content) {
        return new Result(content);
    }

    public static <T> Result<T> success(T content, String message) {
        Result<T> result = new Result(content);
        result.setMessage(message);
        return result;
    }
}
