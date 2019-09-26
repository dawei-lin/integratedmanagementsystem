package com.infinova.imscommon.beans.res;
/**
 * @Project:performancestatistics
 * @File:null.java
 * @Package:com.infinova.performancestatistics.utils
 * @Description:返回结果
 * @author:ldw
 * @version:V1.0
 * @Date:2019/3/4 16:38
 * @Copyright:2019, ldw@szinfinova.com All Rights Reserved.
 */

/**
 * @ClassName:null.java
 * @Description:返回结果
 * @author:ldw
 * @date:2019/3/4 16:38
 *
 *
 */
public class Result {

    private static final String ERROR = "system.desc.error";

    private static final String SUCCESS = "system.desc.success";

    private static final String ERROR_CODE = "0";

    private static final String SUCCESS_CODE = "1";

    public String code;

    public String desc;

    public Result() {
        this.code = SUCCESS_CODE;
        this.desc = SUCCESS;
    }

    public void setData() {
        this.desc = ERROR;
        this.code = ERROR_CODE;
    }

    public void setData(String code,String data) {
        this.code = code;
        this.desc = data;
    }

    @Override
    public String toString() {
        return "Result{" +
                "code='" + code + '\'' +
                ", data='" + desc + '\'' +
                '}';
    }
}
