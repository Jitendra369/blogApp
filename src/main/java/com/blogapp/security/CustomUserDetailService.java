package com.blogapp.security;

import com.blogapp.entities.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        loding user from database by username
        User user = this.userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("Email", "email not Found", -1));
//         we can return user , because user implement UserDetails , it has all the method for getting user details
        return user;
    }
}
