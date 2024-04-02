package com.song.config;

import com.song.filter.JwtAuthenticationTokenFiler;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * spring security 配置
 *
 * @author song
 * @date 2024/04/01
 */
@Configuration
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtAuthenticationTokenFiler jwtAuthenticationTokenFiler;
    /**
     * 身份验证管理器bean
     *
     * @return {@link AuthenticationManager}
     * @throws Exception 例外
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
                // 关闭csrf
                .csrf().disable()
                //不通过session获取SecurityContext
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                //放行登录接口 允许匿名访问
                .antMatchers("/user/login").anonymous()
                //其他接口需要认证
                .anyRequest().authenticated();
        //添加jwt过滤器
        http.addFilterBefore(jwtAuthenticationTokenFiler, UsernamePasswordAuthenticationFilter.class);

    }

    /**
     * 密码加密 8表示加密强度 4-31
     *
     * @return 密码加密对象
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }


}
