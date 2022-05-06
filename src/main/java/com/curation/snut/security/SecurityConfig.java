package com.curation.snut.security;

import com.curation.snut.security.filter.ApiCheckFilter;
import com.curation.snut.security.filter.ApiLoginFilter;
import com.curation.snut.security.handler.LoginFailHandler;
import com.curation.snut.security.handler.LoginSuccessHandler;
import com.curation.snut.security.service.MemberUDService;
import com.curation.snut.security.util.JWTUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
@EnableWebSecurity(debug = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MemberUDService memberUDService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
//        http.cors().disable();
        http.formLogin().disable();
        http.logout().permitAll();
        http.sessionManagement()
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.httpBasic().disable();
        http.authorizeRequests()
                .antMatchers("/register").permitAll()
                .antMatchers("/jwt/login").permitAll()
                .antMatchers("/get/**/*").permitAll()
                .anyRequest().permitAll();
//        http.rememberMe().tokenValiditySeconds(60 * 60 * 24 * 7).userDetailsService(memberUDService);
        http.addFilterBefore(apiCheckFilter(), UsernamePasswordAuthenticationFilter.class);
        http.addFilterBefore(apiLoginFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    public LoginSuccessHandler successHandler() {
        return new LoginSuccessHandler();
    }

    @Bean
    public ApiLoginFilter apiLoginFilter() throws Exception {
        ApiLoginFilter apiLoginFilter = new ApiLoginFilter("/jwt/login", jwtUtil());
        apiLoginFilter.setAuthenticationManager(authenticationManager());

        apiLoginFilter.setAuthenticationFailureHandler(new LoginFailHandler());
        return apiLoginFilter;
    }

    @Bean
    public JWTUtil jwtUtil() {
        return new JWTUtil();
    }

    @Bean
    public ApiCheckFilter apiCheckFilter() {
        return new ApiCheckFilter("/api/**/*", jwtUtil());
    }

}
