package com.infinova.imscommon.beans.res;


/**
 * File: PageInfo.java
 *
 */
public class PageInfo {
	
	// 下一页
	private int page=1;

	// 当前页
	private int currentPage = 1;

	// 每页个数
	private int limit=20;

	// 总条数
	private int totalCount = 0;

	// 总页数
	private int pageCount = 0;
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit <= 0 ? 10 : limit;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public void resetPageNo() {
		page = currentPage + 1;
		pageCount = totalCount % limit == 0 ? totalCount / limit
				: totalCount / limit + 1;
	}


}
