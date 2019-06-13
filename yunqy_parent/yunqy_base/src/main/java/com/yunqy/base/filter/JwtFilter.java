package com.yunqy.base.filter;

import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import utils.JwtUtil;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 37269 on 2018/6/9.
 */
@Component
public class JwtFilter extends HandlerInterceptorAdapter {

    @Autowired
   public JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //System.out.println("经过拦截器！");
        String authHeader = request.getHeader("Authorization");//获取头信息
        if(authHeader!=null){
            if(authHeader.startsWith("Bearer ")){
                String token=authHeader.substring(7);//提取token,Bearer占用了7个字符，其中有一个空格
                Claims claims = jwtUtil.parseJWT(token);
                if(claims!=null){
                    if("admin".equals(claims.get("roles"))){
                        request.setAttribute("admin_claims",claims);
                    }
                    if("user".equals(claims.get("roles"))){
                        request.setAttribute("user_claims",claims);
                    }
                }
            }
        }
        return true;
    }
}
