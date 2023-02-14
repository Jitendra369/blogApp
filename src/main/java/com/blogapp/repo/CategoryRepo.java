package com.blogapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapp.entities.Categeory;

public interface CategoryRepo extends JpaRepository<Categeory, Integer> {

	
}
