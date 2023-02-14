package com.blogapp.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import com.blogapp.payload.PostResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import com.blogapp.entities.Categeory;
import com.blogapp.entities.Post;
import com.blogapp.entities.User;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.repo.CategoryRepo;
import com.blogapp.repo.PostRepo;
import com.blogapp.repo.UserRepo;
import com.blogapp.services.PostService;
import com.blogapp.payload.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class PostServiceImpl  implements PostService{

	@Autowired
	private PostRepo postRepo;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
//		get user
		User user = this.userRepo.findById(userId)
		.orElseThrow(()-> new ResourceNotFoundException("User", "userId", userId));
		
//		get the category
		 Categeory catg=  this.categoryRepo.findById(categoryId)
		.orElseThrow(()-> new ResourceNotFoundException("category", "catId", categoryId));
		
//		get the Post
		Post post = mapper.map(postDto, Post.class);
		post.setPostImage("default.png");
		post.setPostAddedDate(new Date());
		post.setCategeory(catg);
		post.setUser(user);
		
		Post savePost = this.postRepo.save(post);
		return this.mapper.map(savePost, PostDto.class);
	}


	@Override
	public PostDto getPost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		PostDto postDto = this.mapper.map(post, PostDto.class);
		return postDto;
	}

	@Override
	public PostResponse getPostByUser(Integer userId, Integer pageNumber, Integer pageSize) {
//		get the User from userId
//		todo: add sortDir
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user", "userid", userId));
//		get all the post through User object
//		List<Post> postList = this.postRepo.findByUser(user);
		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		Page<Post> userPages = this.postRepo.findByUser(user,pageable);
//		map Post to PostDto
		List<PostDto> postDtoList = userPages.stream()
				.map(post -> this.mapper.map(post, PostDto.class))
				.collect(Collectors.toList());


		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtoList);
		postResponse.setTotalElement(userPages.getTotalElements());
		postResponse.setPageNumber(userPages.getNumber());
		postResponse.setLastPage(userPages.isLast());
		postResponse.setPageSize(userPages.getSize());
		postResponse.setTotalPages(userPages.getTotalPages());

		return postResponse;
	}

	@Override
	public PostResponse getPostByCategory(Integer categId, Integer pageNumber, Integer pageSize) {
//		todo: add sortDir
		Categeory categeory = this.categoryRepo.findById(categId)
				.orElseThrow(() -> new ResourceNotFoundException("category", "catId", categId));

		Pageable pageable = PageRequest.of(pageNumber, pageSize);

		Page<Post> categPostPages = this.postRepo.findByCategeory(categeory, pageable);
		List<PostDto> allPostPagesList = categPostPages.stream()
				.map((post -> this.mapper.map(post, PostDto.class)))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(allPostPagesList);
		postResponse.setPageSize(categPostPages.getSize());
		postResponse.setLastPage(categPostPages.isLast());
		postResponse.setPageNumber(categPostPages.getNumber());
		postResponse.setTotalElement(categPostPages.getTotalElements());
		postResponse.setTotalPages(categPostPages.getTotalPages());
		return  postResponse;
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

//		int pageSize = 1;
//		int pageNumber = 1;
//		apply sortBy
		Sort sort = sortDir.equalsIgnoreCase("asc") ?  Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
//		Sort sort = null;
//		if (sortDir.equalsIgnoreCase("asc")){
//			sort = Sort.by(sortBy).ascending();
//		}else{
//			sort = Sort.by(sortBy).descending();
//		}
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = this.postRepo.findAll(pageable);
		List<Post> content = pagePost.getContent();

//		List<Post> allPosts = this.postRepo.findAll()
		List<PostDto> allPosts = pagePost.stream()
				.map((post -> mapper.map(post, PostDto.class)))
				.collect(Collectors.toList());

//		setting the page details in Page Response
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(allPosts);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElement(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public void deletePost(Integer postId) {  // get post by postId
//	get the post
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post", postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostDto UpdatePost(PostDto postDto, Integer postId) {
//		chek if the post present or not
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		post.setPostContent(postDto.getPostContent());
		post.setPostImage(postDto.getPostImage());
		post.setPostTitle(postDto.getPostTitle());
//		post.setPostAddedDate(new Date());  // original post date
		this.postRepo.save(post);  // save the post

		PostDto postDto2 = this.mapper.map(post, PostDto.class);
		return postDto2;
	}

//	search feature- general search method
	@Override
	public List<PostDto> searchPost(String title){
		List<Post> allPostSearch = this.postRepo.findByPostTitleContaining(title);
		List<PostDto> allSearchedPost = allPostSearch.stream()
				.map(post -> this.mapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return allSearchedPost;
	}

	@Override
	public List<PostDto> searchContent(String content) {
		List<Post> allContentPost = this.postRepo.findByPostContentContaining(content);
		List<PostDto> collect = allContentPost.stream()
				.map(post -> this.mapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return collect;
	}


}
