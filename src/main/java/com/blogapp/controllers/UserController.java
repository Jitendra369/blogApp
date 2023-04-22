package com.blogapp.controllers;


import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.blogapp.impl.UserServiceImpl;
import com.blogapp.payload.APIResponse;
import com.blogapp.payload.UserDto;

import jakarta.validation.Valid;


@RestController
@RequestMapping("/api/users")
public class UserController {

//	this is front-end to user , so here data comes to UserDto
	
	@Autowired
	private UserServiceImpl userServiceImpl;
	
//	POST - create user
	@PostMapping("/")
	private ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto userDtoSaved =  this.userServiceImpl.createUser(userDto);
		return new ResponseEntity<>(userDtoSaved, HttpStatus.CREATED);
	}
	
//	PUT - update user
	@PutMapping("/{userId}")
	private ResponseEntity<UserDto> updateUser(@Valid @PathVariable("userId") int id, @RequestBody UserDto userDto){
		UserDto updateUser = this.userServiceImpl.updateUser(userDto, id);
		return new ResponseEntity<>(updateUser, HttpStatus.CREATED);
	}
	
//	DELETE - delete user
	@DeleteMapping("/{userId}")
	private ResponseEntity<APIResponse> deleteUser(@PathVariable("userId") Integer id){
		this.userServiceImpl.deleteUser(id);
		return new ResponseEntity<APIResponse>(new APIResponse("user deleted",true), HttpStatus.OK);
	}
	
//	GET - get user
	@GetMapping("/{userId}")
	public ResponseEntity<?> getUser(@PathVariable("userId") Integer userId){
		UserDto userDto = this.userServiceImpl.getUserById(userId);
		return new ResponseEntity<>(userDto, HttpStatus.OK);
	}
	
//	GET - all users
	@GetMapping("/allusers")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		List<UserDto> allUsers = this.userServiceImpl.getAllUsers();
		return new ResponseEntity<List<UserDto>>(allUsers, HttpStatus.OK);
	}

	
	
}
