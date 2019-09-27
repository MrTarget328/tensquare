package com.tensquare.manager.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
@Component
public class ManagerFilter extends ZuulFilter {

    @Autowired
    private JwtUtil jwtUtil;
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
        return true;
    }

    @Override
    public Object run() throws ZuulException {
        System.err.println("进来了");
        //获取上下文
        RequestContext currentContext = RequestContext.getCurrentContext();
        HttpServletRequest request = currentContext.getRequest();
        if (request.getMethod().equals("OPTIONS")){
            return null;
        }
        String url = request.getRequestURI();
        if (url.indexOf("admin/login")>0){
            System.err.println("登录页面"+url);
            return null;
        }
        //获取请求头
         String header = request.getHeader("Authorization");
         if (StringUtils.isNotEmpty(header)&& header.startsWith("Bearer ")){
             String token = header.substring(7);
             Claims claims = jwtUtil.parseJWT(token);
             if (claims!=null){
                if (claims.get("roles").equals("admin")){
                    currentContext.addZuulRequestHeader("Authorization",header);
                    return null;
                }
             }
         }
         currentContext.setSendZuulResponse(false);
         currentContext.setResponseStatusCode(403);
         currentContext.setResponseBody("无权访问");
         currentContext.getResponse().setContentType("text/html;charset=UTF-8");
        return null;
    }
}
