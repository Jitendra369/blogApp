package com.blogapp.entities;

import java.util.ArrayList;
import java.util.List;

import org.modelmapper.internal.asm.tree.IntInsnNode;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "categories")
public class Categeory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer cateId;
	
	@Column(name = "title", length = 100, nullable = false)
	private String cateTitle;
	
	@Column(name="description")
	private String cateDescription;
	
//	mapping post to category (oneToMany) >> single category has many Posts
//	mapped by make sure the column creation of categeory given to Post- table 
//	but we can access the post through categeory
//	cascade all -> if parent add then add all it's child, if parent remove then remove 
//	all its child
	
//	if we fetch the category then all we got all the post 
	@OneToMany(mappedBy = "categeory", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<Post> posts = new ArrayList<>();
}
