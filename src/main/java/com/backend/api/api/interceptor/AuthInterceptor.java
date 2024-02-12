package com.backend.api.api.interceptor;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.backend.api.api.Exception.JwtException;
import com.backend.api.api.model.User;
import com.backend.api.api.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    JwtUtil jwt;
    private User user;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            String bearerToken = request.getHeader("Authorization");
            if (bearerToken == null) {
                throw new JwtException("No token found");
            }
            String token = bearerToken.split("Bearer ")[1];
            if (token == null) {
                throw new JwtException("No token found");
            }
            User user = jwt.validateTokenAndRetrieveSubject(token);
            request.setAttribute("user", user);
            return true;
        } catch (JWTVerificationException e) {
            String message = e.getMessage() != null ? e.getMessage() : "token expired or invalid token login again";
            throw  new JwtException(message);
        } catch (Exception e) {
            String message = e.getMessage() != null ? e.getMessage() : "Internal server error";
            throw  new JwtException(message);
        }
    }
    @Override
    public void postHandle(
            HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {}

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                Object handler, Exception exception) throws Exception {}
}
