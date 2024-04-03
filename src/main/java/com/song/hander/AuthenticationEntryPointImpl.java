package com.song.hander;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.song.pojo.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 身份验证入口点impl
 *
 * @author song
 * @date 2024/04/03
 */
@Component
public class AuthenticationEntryPointImpl implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        // Convert the Result object to JSON string
        String json = new ObjectMapper().writeValueAsString(Result.fail("未授权"));
        // Write the JSON string to the response
        response.getWriter().write(json);
        // Flush the response writer
        response.getWriter().flush();

    }
}
