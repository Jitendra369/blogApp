package com.blogapp.services;

import java.util.List;

import com.blogapp.payload.UserDto;

public interface UserService {

//	all CRUD operations 
	UserDto createUser(UserDto user); // creating user 
	UserDto updateUser(UserDto user, Integer userId);
	UserDto getUserById(Integer userId);
	List<UserDto> getAllUsers();
	void deleteUser(Integer userId);
	
}
