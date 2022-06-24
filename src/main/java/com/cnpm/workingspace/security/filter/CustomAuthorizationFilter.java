package com.cnpm.workingspace.security.filter;

import com.cnpm.workingspace.constants.ErrorCode;
import com.cnpm.workingspace.dto.Message;
import com.cnpm.workingspace.security.jwt.JwtUtils;
import com.cnpm.workingspace.security.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.util.Collections.emptyList;

public class CustomAuthorizationFilter extends OncePerRequestFilter {

    private JwtUtils jwtUtils;

    private ObjectMapper mapper = new ObjectMapper();

    public CustomAuthorizationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        filterChain.doFilter(request,response);
//        if(request.getServletPath().startsWith("/api/auth/")){
//            System.out.println("path (pass): "+request.getServletPath());
//            filterChain.doFilter(request,response);
//            return;
//        } else{
//            System.out.println("path : "+request.getServletPath());
//        }
//        SecurityContextHolder.getContext().setAuthentication(null);
//        String authorizationHeader=request.getHeader("Authorization");
//        if(authorizationHeader!=null){
//            String token=authorizationHeader;
//            if(!authorizationHeader.startsWith("Bearer ")){
//                System.out.println("invalid token");
//                response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                response.setContentType("json/application");
//                response.getWriter().write(mapper.writeValueAsString(new ErrorResponse(ErrorCode.INVALID_TOKEN,null)));
//                return;
//            }
//            token=token.replace("Bearer ","");
//            System.out.println("get token from request : "+token);
//            try{
//                String username=jwtUtils.getNameFromJwtToken(token);
//                System.out.println("filter get name : "+username);
//                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=
//                        new UsernamePasswordAuthenticationToken(username,null,emptyList());
//                SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
//                System.out.println("Success !!!");
//                filterChain.doFilter(request,response);
//            } catch (Exception e){
//                System.out.println("incorrect token");
//                SecurityContextHolder.getContext().setAuthentication(null);
//                response.setStatus(HttpStatus.UNAUTHORIZED.value());
//                response.setContentType("json/application");
//                response.getWriter().write(mapper.writeValueAsString(new ErrorResponse(ErrorCode.INCORRECT_TOKEN,null)));
//                System.out.println("error in incorrect token : "+e.getMessage());
//                e.printStackTrace();
//                return;
//            }
//        } else{
//            response.setStatus(HttpStatus.UNAUTHORIZED.value());
//            response.setContentType("json/application");
//            response.getWriter().write(mapper.writeValueAsString(new ErrorResponse(ErrorCode.MISSING_AUTHORIZATION,null)));
//        }

    }
}