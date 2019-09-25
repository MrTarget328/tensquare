package com.tensquare.qa.interceptor;

import io.jsonwebtoken.Claims;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import util.JwtUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //请求头信息
        String header = request.getHeader("Authorization");
        if (StringUtils.isNotEmpty(header) && header.startsWith("Bearer ")){
            try {
                //获取token
                String token = header.substring(7);
                Claims claims = jwtUtil.parseJWT(token);

                if (claims != null && "admin".equals(claims.get("roles"))){
                    request.setAttribute("admin_claims",claims);
                }
                if (claims != null && "user".equals(claims.get("roles"))){
                    request.setAttribute("user_claims",claims);
                }
            }catch (Exception e){
                throw new RuntimeException("权限不足,token过期");
            }
        }
        return true;
    }
}
