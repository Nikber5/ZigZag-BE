package com.zigzag.auction.exceptionhandler;

import com.zigzag.auction.jwt.JwtTokenFilter;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

public class ExceptionHandlerConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain,
        HttpSecurity> {

    @Override
    public void configure(HttpSecurity http) {
        ExceptionHandlerFilter exceptionHandlerFilter = new ExceptionHandlerFilter();
        http.addFilterBefore(exceptionHandlerFilter, JwtTokenFilter.class);
    }
}
