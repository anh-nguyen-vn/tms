package com.charter.vietnam.tms.model;

public class Pagination {

    private Integer pageIndex;
    private Integer recordPerPage;

    public Pagination() {
        this.pageIndex = 0;
        this.recordPerPage = 100;
    }

    public Pagination(Integer pageIndex, Integer recordPerPage) {
        this.pageIndex = pageIndex;
        this.recordPerPage = recordPerPage;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getRecordPerPage() {
        return recordPerPage;
    }

    public void setRecordPerPage(Integer recordPerPage) {
        this.recordPerPage = recordPerPage;
    }
}
