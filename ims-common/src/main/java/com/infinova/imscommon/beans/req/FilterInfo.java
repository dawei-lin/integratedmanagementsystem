package com.infinova.imscommon.beans.req;

import java.util.List;

/**
 * 过滤信息
 */
public class FilterInfo {
	/**
	 * 拆包大小,注：事务中，包太大或太小性能都不佳，500个资源做一次分包性能会比较好 modify by mx 20190122
	 */
    public static final int MAX_NUM = 500;
    private String hql;
    private List<Object> values;

    public String getHql() {
        return hql;
    }

    public void setHql(String hql) {
        this.hql = hql;
    }

    public List<Object> getValues() {
        return values;
    }

    public void setValues(List<Object> values) {
        this.values = values;
    }
}
