package com.infinova.imscommon.beans.res;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *  返回分页结果和必要参数
 */
public class PageRes<T> implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;

    private PageInfo pageInfo = new PageInfo();

    // 记录
    private List<T> results = new ArrayList<T>();

    public PageInfo getPageInfo() {
        return pageInfo;
    }

    public void setPageInfo(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public List<T> getResults() {
        return results;
    }

    public void setResults(List<T> results) {
        this.results = results;
    }

}
