package com.tensquare.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
* 安全配置类
*/
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /**
         * authorizeRequests  认证
         * antMatchers 添加要拦截的路径  permitAll 全部放行
         * authenticated 授权
         * csrf().disable();  关闭攻击功能
         */
        http
            .authorizeRequests()
            .antMatchers("/**").permitAll()
            .anyRequest().authenticated()
            .and().csrf().disable();
    }
}