package com.infinova.authenticationservice.page;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 分页、查询参数解析
 * 
 * @author xrx
 *
 */
public class PageTableArgumentResolver implements HandlerMethodArgumentResolver {
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		Class<?> cla = parameter.getParameterType();

		return cla.isAssignableFrom(PageTableRequest.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);

		PageTableRequest tableRequest = new PageTableRequest();
		
		//post请求在application/json请求方式下无法通过getParameterMap获取参数
		String contentType = request.getContentType();
		if("POST".equals(request.getMethod()) && contentType.contains("application/json")){
			BufferedReader reader = request.getReader();
	        StringBuilder sb = new StringBuilder();
	        char[] buf = new char[1024];
	        int rd;
	        while((rd = reader.read(buf)) != -1){
	            sb.append(buf, 0, rd);
	        }
	        JSONObject jsonObject = JSONObject.parseObject(sb.toString());
	        Map<String, Object> params = JSONObject.parseObject(jsonObject.toJSONString(), new TypeReference<Map<String, Object>>(){});
	        if(!params.containsKey("currentPage")||!params.containsKey("pageSize")){
				return null;
			}
	        int currentPage = Integer.parseInt(params.get("currentPage").toString());
			int pageSize = Integer.parseInt(params.get("pageSize").toString());
			tableRequest.setOffset((currentPage-1)*pageSize);
			tableRequest.setLimit(pageSize);
			tableRequest.setParams(params);
			return tableRequest;
		}
		
		Map<String, String[]> param = request.getParameterMap();
		if(!param.containsKey("currentPage")||!param.containsKey("pageSize")){
			return null;
		}
		int currentPage = Integer.parseInt(request.getParameter("currentPage"));
		int pageSize = Integer.parseInt(request.getParameter("pageSize"));
		tableRequest.setOffset((currentPage-1)*pageSize);
		tableRequest.setLimit(pageSize);

		Map<String, Object> map = Maps.newHashMap();
		tableRequest.setParams(map);

		param.forEach((k, v) -> {
			if (v.length == 1) {
				map.put(k, v[0]);
			} else {
				map.put(k, Arrays.asList(v));
			}
		});

		//setOrderBy(tableRequest, map);
		//removeParam(tableRequest);

		return tableRequest;
	}

	/**
	 * 去除datatables分页带的一些复杂参数
	 * 
	 * @param tableRequest
	 */
	private void removeParam(PageTableRequest tableRequest) {
		Map<String, Object> map = tableRequest.getParams();

		if (!CollectionUtils.isEmpty(map)) {
			Map<String, Object> param = new HashMap<>();
			map.forEach((k, v) -> {
				if (k.indexOf("[") < 0 && k.indexOf("]") < 0 && !"_".equals(k)) {
					param.put(k, v);
				}
			});

			tableRequest.setParams(param);
		}
	}

	/**
	 * 从datatables分页请求数据中解析排序
	 * 
	 * @param tableRequest
	 * @param map
	 */
	private void setOrderBy(PageTableRequest tableRequest, Map<String, Object> map) {
		StringBuilder orderBy = new StringBuilder();
		int size = map.size();
		for (int i = 0; i < size; i++) {
			String index = (String) map.get("order[" + i + "][column]");
			if (StringUtils.isEmpty(index)) {
				break;
			}
			String column = (String) map.get("columns[" + index + "][data]");
			if (StringUtils.isBlank(column)) {
				continue;
			}
			String sort = (String) map.get("order[" + i + "][dir]");

			orderBy.append(column).append(" ").append(sort).append(", ");
		}

		if (orderBy.length() > 0) {
			tableRequest.getParams().put("orderBy",
					" order by " + StringUtils.substringBeforeLast(orderBy.toString(), ","));
		}
	}
	
}
