package com.song.config;

import com.song.filter.JwtAuthenticationTokenFiler;
import com.song.hander.AccessDeniedHandlerImpl;
import com.song.hander.AuthenticationEntryPointImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
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
//开启方法级别的权限控制
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final JwtAuthenticationTokenFiler jwtAuthenticationTokenFiler;
    private final AccessDeniedHandlerImpl accessDeniedHandler;
    private final AuthenticationEntryPointImpl authenticationEntryPoint;

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
                //放行登录接口 必须是匿名的 如果不是匿名的会被拦截
                .antMatchers("/user/login").anonymous()
                //其他接口需要认证
                .anyRequest().authenticated();

        //添加jwt过滤器
        http.addFilterBefore(jwtAuthenticationTokenFiler, UsernamePasswordAuthenticationFilter.class);
        //配置异常处理
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint);
        //跨域
        http.cors();

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
