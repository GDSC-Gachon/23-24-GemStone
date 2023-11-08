package com.gemstone.securitystudy.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
// 스프링 시큐리티 5.7 이전. 스프링 부트 2.7 이전
public class SecurityConfig_old extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/login").permitAll()
                .antMatchers("/users/**", "/settings/**").hasAuthority("Admin")
                .antMatchers("#").hasAnyAuthority("Admin", "Editor", "Salesperson")
                .antMatchers("#").hasAnyAuthority("Admin", "Editor", "Salesperson", "Shipper")
                .anyRequest().authenticated()
                .and().formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .permitAll()
                .and()
                .logout().permitAll();

        http.headers().frameOptions().sameOrigin();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }
}
