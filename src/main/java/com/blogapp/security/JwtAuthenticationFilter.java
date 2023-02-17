package com.blogapp.security;

import com.blogapp.impl.UserServiceImpl;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

//creating the filer and insert into series of filter that springboot use
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        get token
        String requestToken = request.getHeader("Authorization");

        System.out.println(requestToken);
        String username = null;
        String token = null;

        if(requestToken!= null && requestToken.startsWith("Bearer ")){
            token = requestToken.substring(7);

            try{
                username = this.jwtTokenHelper.getUsernameFromToken(token);
            }catch (IllegalArgumentException e){
                System.out.println("unable to get token");
            }catch (ExpiredJwtException ex){
                System.out.println("invalid jwt");
            }

        }else{
            System.out.println("Request TOken is null or not start with Bearer");
        }

//      check username null as well securityContet
        if (username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
//            validate the token
            if (this.jwtTokenHelper.validateToken(token, userDetails)){
//              if token is validate then, create usernamePasswordAuthentication Token
//                set userDetials , userRole
                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails,null, userDetails.getAuthorities());
                /* When a user attempts to authenticate, Spring Security creates an Authentication
                object that represents the user's credentials. The WebAuthenticationDetailsSource
                is responsible for creating additional details about the authentication event,
                such as the user's IP address and browser information, and adding those
                details to the Authentication object. */
                usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                save the authentication-token to security-context
                SecurityContextHolder.getContext().setAuthentication( usernamePasswordAuthenticationToken);
            }else{
                System.out.println("Invlid Jwt Token");
            }
        }else{
            System.out.println("username is null and content is null");
        }

//        forward the filer chain, passing the request to next filter
        filterChain.doFilter(request,response);
    }
}
