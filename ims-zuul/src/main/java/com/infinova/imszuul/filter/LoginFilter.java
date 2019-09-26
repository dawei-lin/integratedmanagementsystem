package com.infinova.imszuul.filter;
/**
 * @Project:integratedmanagementsystem
 * @File:LoginFilter
 * @Package:com.infinova.imszuul.filter
 * @Description:
 * @author:ldw
 * @version:V1.0
 * @Date:2019/7/19 16:37
 * @Copyright:2019,ldw@szinfinova.com All Rights Reserved.
 */

import com.infinova.imszuul.bean.res.AjaxMessageResult;
import com.infinova.imszuul.service.CheckService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;


import javax.servlet.http.HttpServletRequest;


/**
 * @ClassName:LoginFilter
 * @Description:
 * @author:ldw
 * @date:2019/7/19 16:37
 */
public class LoginFilter extends ZuulFilter {

    private final Logger log = LoggerFactory.getLogger(LoginFilter.class);

    @Autowired
    @Lazy
    private CheckService checkService;

    @Override
    public String filterType() {
        return "pre";
    }

    @Override
    public int filterOrder() {
        return 0;
    }

    @Override
    public boolean shouldFilter() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        String url = request.getRequestURI();
        System.out.println(url);
        Boolean res = "/auth/logout".equalsIgnoreCase(url);
        res = res || "/auth/login".equalsIgnoreCase(url);
        return !res;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        String url = request.getRequestURI();
        String method = request.getMethod();
        log.info("send  {} request to {} ", method, url);

        String token = getToken(request);
        if (StringUtils.isBlank(token)) {
            log.warn("登录失效");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.addZuulResponseHeader("Content-Type", "application/json;charset=UTF-8");
            ctx.setResponseBody("{\"code\": \"401\",\"desc\": \"登录失效！\"}");
            return null;
        }

        // 对请求进行路由
        ctx.setSendZuulResponse(true);
        ctx.setResponseStatusCode(200);
        ctx.set("isSuccess", true);
        log.info("access token ok");


        //校验 token  获取服务间调用token
        AjaxMessageResult<Object> result = checkService.checkToken(token, url, method);
        log.info("token 校验结果为： {}",result.getCode());
        log.info("token 校验结果为： {}",result.getDesc());
        if ("1".equals(result.getCode())){
            ctx.setSendZuulResponse(true);
            ctx.setResponseStatusCode(200);

            ctx.set("isSuccess", true);
            log.info("access token ok");
            try {
                // 校验通过后更换为一分钟时长token
                token = result.getMsg().toString();
                //把token设置到响应头中去
                ctx.addZuulResponseHeader("token", token);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }else {
            log.warn("权限不足或登录失效");
            ctx.setSendZuulResponse(false);
            ctx.setResponseStatusCode(401);
            ctx.addZuulResponseHeader("Content-Type", "application/json;charset=UTF-8");
            ctx.setResponseBody("{\"code\": \"401\",\"desc\": \"登录失效！\"}");
            return  null;
        }
    }

    private static String getToken(HttpServletRequest request) {
        String token = request.getParameter("token");
        if (StringUtils.isBlank(token)) {
            token = request.getHeader("token");
        }
        return token;
    }

}
