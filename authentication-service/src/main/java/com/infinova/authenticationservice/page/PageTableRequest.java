package com.infinova.authenticationservice.page;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.util.Map;

/**
 * 分页查询参数
 * 
 * @author ldw
 *
 */
@Getter
@Setter
@ToString
//@ApiModel(value = "PageTableRequest  分页查询请求")
public class PageTableRequest implements Serializable {

	private static final long serialVersionUID = 7328071045193618467L;

	//@ApiModelProperty(value = "从第几个记录开始读起", dataType = "Integer", required = true, example = "0")
	private Integer offset;
	//@ApiModelProperty(value = "读取记录数", dataType = "Integer", required = true, example = "0")
	private Integer limit;
	//@ApiModelProperty(value = "查询条件", dataType = "Map<String, Object>", required = false)
	private Map<String, Object> params;

}
