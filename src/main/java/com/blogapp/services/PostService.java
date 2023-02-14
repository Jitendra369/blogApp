package com.blogapp.services;

import java.util.List;

import com.blogapp.entities.Categeory;
import com.blogapp.entities.User;
import com.blogapp.payload.PostDto;
import com.blogapp.payload.PostResponse;

public interface PostService {

	//	add
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);

	//	get
	PostDto getPost(Integer postId);

	PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize); //todo: apply pagination

	PostResponse getPostByCategory(Integer categId, Integer pageNumber, Integer pageSize);  //todo: apply paginations

	//	get All
	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);

	//	delete
	void deletePost(Integer postId);

	//	update
	PostDto UpdatePost(PostDto postDto, Integer postId);

	//	search in all post
	List<PostDto> searchPost(String title);

	List<PostDto> searchContent(String content);

}