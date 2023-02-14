package com.blogapp.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapp.entities.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.repo.UserRepo;
import com.blogapp.services.UserService;
import com.blogapp.payload.UserDto;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	

	@Override
	public UserDto createUser(UserDto userDto) {
//		1) get userDto from controller
//		2) convert it into user 
//		3) save it into data-base usign repo
//		4) convert the return user into userDto 
//		5) send userDto to controller 
		
	    User user = this.userRepo.save(getUserFromUserDto(userDto));
	    User savedUser = this.userRepo.save(user);
	    return this.getUserDtoFromUserUser(savedUser);
	}

//	convert useDTo to User
	private User getUserFromUserDto(UserDto userDto) {
//		User user  = new User();
//		user.setName(userDto.getName());
//		user.setEmail(userDto.getEmail());
//		user.setAbout(userDto.getAbout());
//		user.setPassword(userDto.getPassword());
		User user = this.modelMapper.map(userDto, User.class);
		return user;
	}
	
//	convert User to useDTo 
	private UserDto getUserDtoFromUserUser(User user) {
//		UserDto userDto  = new UserDto();
//		userDto.setName(user.getName());
//		userDto.setEmail(user.getEmail());
//		userDto.setAbout(user.getAbout());
//		userDto.setPassword(user.getPassword());
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}

	@Override
	public UserDto updateUser(UserDto user, Integer userId) {
		
//		1) get the user from data-base 
//		2) update its values
//		3) save into database 
//		4) return the userDTO object
		
		User user2 = this.userRepo.findById(userId)
				.orElseThrow(()-> new ResourceNotFoundException("User","id", userId));
		
		user2.setName(user.getName());
		user2.setEmail(user.getEmail());
		user2.setAbout(user.getAbout());
		user2.setPassword(user.getPassword());
		
		User saveUser = this.userRepo.save(user2);		
		return getUserDtoFromUserUser(saveUser);

	}

	@Override
	public UserDto getUserById(Integer userId) {
//		get user 
		User user = this.userRepo.findById(userId)
		.orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
		
		return getUserDtoFromUserUser(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		
//		get All user from repo
		List<User> users =  this.userRepo.findAll();
		
		List<UserDto> allUsers =  users.stream()
					.map(user -> this.getUserDtoFromUserUser(user))
					.collect(Collectors.toList());
		
//		return list of collectors 
		return allUsers;
	}

	@Override
	public void deleteUser(Integer userId) {
//		first check if user Present in the db
		User user = this.userRepo.findById(userId)
		.orElseThrow(()-> new ResourceNotFoundException("User", "id", userId));
		
		if (user!=null) {
			this.userRepo.deleteById(userId);
		}
		
	}

}