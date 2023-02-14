package com.blogapp.payload;

import com.blogapp.entities.Categeory;
import com.blogapp.entities.Comment;
import com.blogapp.entities.User;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

	private Integer postId;

	private String postTitle;
	
	private String postContent;
	
	private String postImage;
	
	private Date postAddedDate;
	
	private CategotyDto categeory;
	
	private UserDto user;

	private Set<Comment> comment = new HashSet<>();
}
