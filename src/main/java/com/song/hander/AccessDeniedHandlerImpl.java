package com.song.hander;

import cn.hutool.json.JSONUtil;
import com.song.pojo.Result;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 拒绝访问处理程序impl
 *
 * @author song
 * @date 2024/04/03
 */
@Component
public class AccessDeniedHandlerImpl implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        String jsonStr = JSONUtil.toJsonStr(Result.fail("权限不足"));
        response.getWriter().write(jsonStr);
        response.getWriter().flush();

    }
}
