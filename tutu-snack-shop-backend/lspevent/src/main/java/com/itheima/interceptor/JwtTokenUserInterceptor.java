package com.itheima.interceptor;

import com.itheima.context.BaseContext;
import com.itheima.properties.JwtProperties;
import com.itheima.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
@Slf4j
public class JwtTokenUserInterceptor implements HandlerInterceptor {
    public static final String USER_ID = "userId";
    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 校验jwt
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }

        //1、从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getUserTokenName());

        //2、校验令牌
        try {
            if (token != null && !token.isEmpty()) {
                log.info("jwt校验:{}", token);
                Claims claims = JwtUtils.parseJWT(jwtProperties.getUserSecretKey(), token);
                Long userId = Long.valueOf(claims.get(USER_ID).toString());
                log.info("当前用户的id：{}", userId);
                BaseContext.setCurrentId(userId);
            } else {
                // 没有token时，默认使用用户ID为1
                log.info("未提供token，使用默认用户ID: 1");
                BaseContext.setCurrentId(1L);
            }
            //3、通过，放行
            return true;
        } catch (Exception ex) {
            log.error("jwt校验失败，使用默认用户ID: 1", ex);
            BaseContext.setCurrentId(1L);
            return true;
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 清理ThreadLocal，避免内存泄漏
        BaseContext.removeCurrentId();
    }
}
