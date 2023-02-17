package com.blogapp.controllers;

import com.blogapp.exceptions.JwtTokenException;
import com.blogapp.payload.JwtAuthRequest;
import com.blogapp.security.JWTTokenHelper;
import com.blogapp.security.JwtAuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    @Autowired
    private JWTTokenHelper jwtTokenHelper;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

//    we get the username and password from the user
//    then we generate the jwt token
//    send token to user
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponse> createToken(
            @RequestBody JwtAuthRequest jwtAuthRequest
    ) throws Exception {

        authenticateUser(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());
        String token = this.jwtTokenHelper.generateToken(userDetails);
        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setToken(token);

        return new ResponseEntity<JwtAuthResponse>(jwtAuthResponse, HttpStatus.OK);
    }

    private void authenticateUser(String username, String password) throws Exception {
        UsernamePasswordAuthenticationToken userPassTOken = new UsernamePasswordAuthenticationToken(username, password);
       try{
           this.authenticationManager.authenticate(userPassTOken);
       }catch (BadCredentialsException exception){
        throw  new JwtTokenException("authentication error","username and pasword does not match");
       }
    }
}
