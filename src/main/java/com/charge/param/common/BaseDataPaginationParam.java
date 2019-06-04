package com.charge.param.common;

import java.io.Serializable;

public class BaseDataPaginationParam implements Serializable {

    private static final long serialVersionUID = 3025327640570749120L;

    /**
     * 当前页数
     */
    private Integer currentPage = 1;

    /**
     * 每页显示条数
     */
    private Integer pageSize = 10;


    public Integer getCurrentPage() {
        return currentPage == null ? 1 : currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize == null ? 100 : pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

}
