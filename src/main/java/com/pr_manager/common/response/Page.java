package com.pr_manager.common.response;

/**
 *
 * Created by Jo on 2017/5/12.
 */
public class Page {
    private int pageSize;
    private int currentPage;
    private int currentNumber;
    private int totalPage;
    private int totalNumber;

    public int getCurrentNumber() {
        return currentNumber;
    }

    public void setCurrentNumber(int currentNumber) {
        this.currentNumber = currentNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        if (currentPage != 0) currentNumber = (currentPage-1)* pageSize;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        if (pageSize != 0) currentNumber = (currentPage-1)* pageSize;

    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalNumber) {
        if (pageSize != 0) this.totalPage = (int)Math.ceil((double) totalNumber/(double) pageSize);
    }

    public int getTotalNumber() {
        return totalNumber;
    }

    public void setTotalNumber(int totalNumber) {
        this.totalNumber = totalNumber;
        setTotalPage(totalNumber);
    }
}
