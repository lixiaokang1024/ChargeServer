package com.charge.pojo.common;

import java.io.Serializable;

public class PageResultDTO<T> extends ResultDTO<T> implements Serializable {

    private static final long serialVersionUID = 4786964822719835688L;

    /**
     * 当前页，默认第一页
     */
    private int currentPage = 1;

    /**
     * 每页记录数，默认10条 ,当为0时查询全部
     */
    private int pageSize = 10;
    /**
     * 总记录数
     */
    private int totalRecord;
    /**
     * 总页数
     */
    private int totalPage;

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(int totalRecord) {
        int totalPage = 0;
        if (totalRecord % pageSize == 0) {
            totalPage = totalRecord / pageSize;
        } else {
            totalPage = totalRecord / pageSize + 1;
        }
        this.totalPage = totalPage;
        this.totalRecord = totalRecord;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }


}
