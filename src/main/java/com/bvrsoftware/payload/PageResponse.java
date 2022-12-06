package com.bvrsoftware.payload;

public class PageResponse<T> {
    private T data;
    private Integer totalPage;
    private Integer currentPage;

    public PageResponse() {
    }

    public PageResponse(T data, Integer totalPage) {
        this.data = data;
        this.totalPage = totalPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }
}
