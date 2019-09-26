package com.infinova.imscommon.beans.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 分页请求类
 *  ldw
 */
@Getter
@Setter
@ApiModel(value = "PageReq  分页参数对象")
public class PageReq implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 1L;
    
    public static final int LIMIT = 10000;

    /**
     * 请求页码
     */
    @ApiModelProperty(value = "请求页码", dataType = "Integer", required = true)
    private Integer page = 1;

    /**
     * 单页获取最大记录总数
     */
    @ApiModelProperty(value = "单页获取最大记录总数", dataType = "Integer", required = true)
    private Integer limit = 20;

    /**
     * 过滤条件:是一个包含多项过滤条件对象的json字符串
     */
    @ApiModelProperty(value = "过滤条件json字符串", dataType = "String", required = true)
    private String filter = "";

    /**
     * 排序条件，是一个包含多项排序条件对象的json字符串
     */
    @ApiModelProperty(value = "排序条件", dataType = "String", required = true)
    private String sort = "";


}
