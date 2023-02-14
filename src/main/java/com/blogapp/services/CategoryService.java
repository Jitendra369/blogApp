package com.blogapp.services;

import java.util.List;

import com.blogapp.payload.CategotyDto;

public interface CategoryService {

//	create
	CategotyDto saveCategotyDto(CategotyDto categotyDto);
	
//	update 
	CategotyDto updateCategotyDto(CategotyDto categotyDto, Integer catId);
	
//	delete 
	void deleteCategory(Integer cateId);
	
//	get
	CategotyDto getCategotyDto(Integer cateId);
	
//	getAll
	List<CategotyDto> getAllCateg();
}
