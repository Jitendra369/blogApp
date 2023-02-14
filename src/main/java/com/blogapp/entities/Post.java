package com.blogapp.entities;

import jakarta.persistence.*;
import org.hibernate.boot.model.process.internal.InferredBasicValueResolution;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="posts")
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;
	
	@Column(name = "title", nullable = false, length = 100)
	private String postTitle;
	
	@Column(name = "content", nullable = false)
	private String postContent;
	
	private String postImage;
	private Date postAddedDate;
	
//	category mapping (manyToOne) --> many post mapped to single category
	@ManyToOne
	@JoinColumn(name = "categeoryId")
	private Categeory categeory;
	
//	many post mapped with single User
	@ManyToOne
	@JoinColumn(name = "userId")
	private User user;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
	private Set<Comment> comments = new HashSet<>();
}
