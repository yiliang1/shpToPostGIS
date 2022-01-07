package com.tlw.storagemanagement.utils;

import org.springframework.data.domain.Page;

import java.util.List;

public class PageResult<T> extends Result<List<T>> {
    private boolean first;
    private boolean last;
    private int number;
    private int numberOfElements;
    private int size;
    private long totalElements;
    private int totalPages;

    public PageResult() {
    }

    public PageResult(Page<T> page) {
        this.initByPage(page);
    }

    public PageResult(int status, String message, Page<T> page) {
        super(status, message);
        this.initByPage(page);
    }

    private void initByPage(Page<T> page) {
        this.setFirst(page.isFirst());
        this.setLast(page.isLast());
        this.setNumber(page.getNumber());
        this.setNumberOfElements(page.getNumberOfElements());
        this.setSize(page.getSize());
        this.setTotalElements(page.getTotalElements());
        this.setTotalPages(page.getTotalPages());
        this.setContent(page.getContent());
    }

    public boolean isFirst() {
        return this.first;
    }

    public void setFirst(boolean first) {
        this.first = first;
    }

    public boolean isLast() {
        return this.last;
    }

    public void setLast(boolean last) {
        this.last = last;
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumberOfElements() {
        return this.numberOfElements;
    }

    public void setNumberOfElements(int numberOfElements) {
        this.numberOfElements = numberOfElements;
    }

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public long getTotalElements() {
        return this.totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return this.totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
