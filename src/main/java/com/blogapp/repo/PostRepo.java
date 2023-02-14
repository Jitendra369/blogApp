package com.blogapp.repo;

import java.util.List;

import com.blogapp.payload.PostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.entities.Categeory;
import com.blogapp.entities.Post;
import com.blogapp.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

//	get all post by user 
//	List<Post> findByUser(User user);
	
//	get all Post by categeory
	Page<Post> findByCategeory(Categeory categeory, Pageable pageable);

	Page<Post> findByUser(User user, Pageable pageable);

//	search in all post's tile
	List<Post> findByPostTitleContaining(String title);

//	search in all post's content
	List<Post> findByPostContentContaining(String content);

//	search user's  post's tile
//todo: search in post title for specific keyword
	List<Post> findByPostTitleContainingAndUser(String title, User user);

//	search user's  post's tile
//	todo: search for any cotent in the Post for specific user
	List<Post> findByCategeoryAndUser(Categeory categeory, User user);

	
}
