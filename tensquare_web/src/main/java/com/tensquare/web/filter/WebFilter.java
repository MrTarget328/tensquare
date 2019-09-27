package com.tensquare.web.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.apache.commons.lang.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class WebFilter extends ZuulFilter {

    /**
     * 在访问之前执行
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 第几个执行  数字越小  执行越早
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * true  代表开启过滤器   false  关闭
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 无论返回任何 数据  都将向下继续执行
     * @return
     * @throws ZuulException
     */
    @Override
    public Object run() throws ZuulException {
        System.err.println("zuul过滤器");
        //向header中添加鉴权令牌
        RequestContext requestContext = RequestContext.getCurrentContext();
        //获取请求头
        HttpServletRequest request = requestContext.getRequest();
         String authorization = request.getHeader("Authorization");
         if (StringUtils.isNotEmpty(authorization)){
             requestContext.addZuulRequestHeader("Authorization",authorization);
         }
        return null;
    }
}
