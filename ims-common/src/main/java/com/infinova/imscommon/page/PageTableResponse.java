package com.infinova.imscommon.page;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * 分页查询返回
 * 
 * @author ldw
 *
 */
@Getter
@Setter
@ApiModel(value = "PageTableResponse  分页查询响应")
public class PageTableResponse implements Serializable {

	private static final long serialVersionUID = 620421858510718076L;

	@ApiModelProperty(value = "记录总数", dataType = "Integer", required = false, example = "0")
	private Integer recordsTotal;
	@ApiModelProperty(value = "记录列表", dataType = "List<Object>", required = false)
	private List<?> data;

	public PageTableResponse(Integer recordsTotal, List<?> data) {
		super();
		this.recordsTotal = recordsTotal;
		this.data = data;
	}

}