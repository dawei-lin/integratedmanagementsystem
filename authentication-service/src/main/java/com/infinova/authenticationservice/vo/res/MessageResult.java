package com.infinova.authenticationservice.vo.res;
/**
 * @Project:performancestatistics
 * @File:null.java
 * @Package:com.infinova.performancestatistics.common
 * @Description:返回结果
 * @author:ldw
 * @version:V1.0
 * @Date:2019/3/5 9:51
 * @Copyright:2019, ldw@szinfinova.com All Rights Reserved.
 */

import java.util.ArrayList;

/**
 * @ClassName:Message.java
 * @Description:返回结果
 * @author:ldw
 * @date:2019/3/5 9:51
 *
 *
 */
public class MessageResult<T> extends Result {

    //list 返回结果   或者 object
    private T msg;

    public MessageResult(T msg) {
        this.msg = msg;
    }

    public T getMsg() {
        return msg;
    }

    public MessageResult() {
    }

    @SuppressWarnings("unchecked")
    public void setMsg(T msg) {
        if (null == msg) {
            msg = (T) new ArrayList<>();
        }
        this.msg = msg;
    }
}
