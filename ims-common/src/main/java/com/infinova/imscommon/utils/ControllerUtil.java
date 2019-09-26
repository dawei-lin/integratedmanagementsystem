package com.infinova.imscommon.utils;
/**
 * @Project:CooperativeProductMS_server
 * @File:ControllerUtil
 * @Package:com.infinova.pcms.pcmscommon.utils
 * @Description:
 * @author:ldw
 * @version:V1.0
 * @Date:2019/8/5 17:57
 * @Copyright:2019,ldw@szinfinova.com All Rights Reserved.
 */

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.infinova.imscommon.beans.page.PageDomain;
import com.infinova.imscommon.beans.page.TableDataInfo;
import com.infinova.imscommon.beans.page.TableSupport;

import java.util.List;

/**
 * @ClassName:ControllerUtil
 * @Description:
 * @author:ldw
 * @date:2019/8/5 17:57
 */
public class ControllerUtil {

    /**
     * 设置请求分页数据
     */
    public static void startPage(PageDomain pageDomain) {
        //PageDomain pageDomain = TableSupport.buildPageRequest();
        System.out.println(pageDomain.toString());
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtil.isNotNull(pageNum) && StringUtil.isNotNull(pageSize)) {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }

    public static void startPage() {
        PageDomain pageDomain = TableSupport.buildPageRequest();
        System.out.println(pageDomain.toString());
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        if (StringUtil.isNotNull(pageNum) && StringUtil.isNotNull(pageSize)) {
            String orderBy = SqlUtil.escapeOrderBySql(pageDomain.getOrderBy());
            PageHelper.startPage(pageNum, pageSize, orderBy);
        }
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    public static TableDataInfo getDataTable(List<?> list) {
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(0);
        rspData.setRows(list);
        rspData.setTotal(new PageInfo(list).getTotal());
        return rspData;
    }
}
