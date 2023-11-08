package com.gemstone.securitystudy.config;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

// how Spring Security internally implements methods like HttpSecurity.authorizeRequests()
public class MyCustomDsl extends AbstractHttpConfigurer<MyCustomDsl, HttpSecurity> {
    @Override
    public void configure(HttpSecurity http) throws Exception {
        AuthenticationManager authenticationManager = http.getSharedObject(AuthenticationManager.class);
//        http.addFilter(new CustomFilter(authenticationManager));
    }

    public static MyCustomDsl customDsl() {
        return new MyCustomDsl();
    }
}
