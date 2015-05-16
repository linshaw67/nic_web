package com.importadorabacco.web.security.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.importadorabacco.web.model.domain.ApiResp;
import com.importadorabacco.web.security.SecurityContext;
import com.importadorabacco.web.security.annotation.Security;
import com.importadorabacco.web.security.constant.Level;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by tanhengyi on 15-5-16.
 */
public class SecurityInterceptor implements HandlerInterceptor {
    @Resource
    private SecurityContext securityContext;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        if (!handlerMethod.getMethod().isAnnotationPresent(Security.class)
                || handlerMethod.getMethodAnnotation(Security.class).value() <= Level.GUEST) {
            return true;
        }

        if (handlerMethod.getMethodAnnotation(Security.class).value() >= Level.MEMBER
                && securityContext.isLogin()) {
            return true;
        }

        securityContext.logout(response);
        response.setHeader("Content-Type", "application/json");
        response.getOutputStream()
                .write(objectMapper.writeValueAsString(
                        new ApiResp<String>(-2, "please login first", "please login first")).getBytes("UTF-8"));

        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) throws Exception {

    }
}
