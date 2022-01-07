package com.tlw.storagemanagement.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityResult {
    private static final Logger logger = LoggerFactory.getLogger(ResponseEntityResult.class);

    public ResponseEntityResult() {
    }

    public static <T> ResponseEntity<Result<T>> error() {
        return error("操作失败");
    }

    public static <T> ResponseEntity<Result<T>> error(String message) {
        return error(0, message);
    }

    public static <T> ResponseEntity<PageResult<T>> pageError(String message) {
        return pageEerror(0, message);
    }

    public static <T> ResponseEntity<Result<T>> error(int code, String message) {
        Result<T> result = new Result(code, message);
        logger.debug("返回出错信息,result={}", result);
        return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
    }

    public static <T> ResponseEntity<PageResult<T>> pageEerror(int code, String message) {
        PageResult result = new PageResult();
        result.setStatus(code);
        result.setMessage(message);
        logger.debug("返回出错信息,result={}", result);
        return new ResponseEntity(result, HttpStatus.BAD_REQUEST);
    }

    public static <T> ResponseEntity<Result<T>> success() {
        Result<T> result = new Result();
        return success(result);
    }

    public static <T> ResponseEntity<Result<T>> success(T content) {
        Result<T> result = new Result();
        result.setContent(content);
        return success(result);
    }

    public static <T> ResponseEntity<Result<T>> success(Result<T> result) {
        logger.debug("返回成功信息,result={}", result);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    public static <T> ResponseEntity<PageResult<T>> success(Page<T> page) {
        PageResult<T> pageResult = new PageResult();
        BeanUtils.copyProperties(page, pageResult);
        return new ResponseEntity(pageResult, HttpStatus.OK);
    }

    public static <T> ResponseEntity<PageResult<T>> success(PageResult<T> pageResult) {
        return new ResponseEntity(pageResult, HttpStatus.OK);
    }
}
