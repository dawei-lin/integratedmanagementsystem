package com.infinova.authenticationservice.security.filter;
/**
 * @Project:springsecuritydemo
 * @File:CustomAuthenticationFilter
 * @Package:com.infinova.springsecuritydemo.filter
 * @Description:
 * @author:ldw
 * @version:V1.0
 * @Date:2019/3/14 16:01
 * @Copyright:2019,ldw@szinfinova.com All Rights Reserved.
 */

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * @ClassName:CustomAuthenticationFilter
 * @Description: json请求登录 过滤器
 * @author: ldw
 * @date:2019/3/14 16:01
 *
 */
public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    //MediaType内没有utf-8为小写格式的类型，前端有时候自己敲的有问题的，引起报错
    private static final String APPLICATION_JSON_LOWER_UTF8_VALUE = "application/json;charset=utf-8";

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {


        if (request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE) ||
                request.getContentType().equals(APPLICATION_JSON_LOWER_UTF8_VALUE)
                || request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE)) {

            ObjectMapper mapper = new ObjectMapper();
            UsernamePasswordAuthenticationToken authRequest = null;

            try (InputStream is = request.getInputStream()) {
                Map<String,String> authenticationBean = mapper.readValue(is, Map.class);
                authRequest = new UsernamePasswordAuthenticationToken(
                        authenticationBean.get("username"), authenticationBean.get("password"));
            } catch (IOException e) {
                e.printStackTrace();
                authRequest = new UsernamePasswordAuthenticationToken(
                        "", "");
            } finally {
                setDetails(request, authRequest);
                return this.getAuthenticationManager().authenticate(authRequest);
            }
        }
        else {
            return super.attemptAuthentication(request, response);
        }
    }
}
