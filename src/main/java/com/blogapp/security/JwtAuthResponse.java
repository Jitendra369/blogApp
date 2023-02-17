package com.blogapp.security;

import lombok.Data;

@Data
public class JwtAuthResponse {
    private String token;  // send jwt token to client
}
