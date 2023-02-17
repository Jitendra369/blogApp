package com.blogapp.exceptions;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenException extends RuntimeException{

    private String title;
    private String message;

}
