package com.blogapp.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blogapp.entities.Categeory;
import com.blogapp.exceptions.ResourceNotFoundException;
import com.blogapp.repo.CategoryRepo;
import com.blogapp.services.CategoryService;
import com.blogapp.payload.CategotyDto;

@Service
public class CategServiceImple implements CategoryService{
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper mapper;

//	save categ
	@Override
	public CategotyDto saveCategotyDto(CategotyDto categotyDto) {
		Categeory categeory = mapper.map(categotyDto, Categeory.class);
		Categeory saveCategeory = this.categoryRepo.save(categeory);
	
		return mapper.map(saveCategeory, CategotyDto.class);
	}

	@Override
	public CategotyDto updateCategotyDto(CategotyDto categotyDto, Integer catId) {
//		check weather the category having id, present in the database or not
//		Optional<Categeory> categOptional = this.categoryRepo.findById(catId);
//		if (categOptional.isPresent()) {
//			Categeory categeory = mapper.map(categotyDto, Categeory.class);
//			Categeory saveCategeory = this.categoryRepo.save(categeory);
//			return mapper.map(saveCategeory, CategotyDto.class);
//		}
//		
//		throw ResourceNotFoundException();
		
		Categeory categeory = this.categoryRepo.findById(catId)
				.orElseThrow(()-> new ResourceNotFoundException("category", "categid", catId));
		
//		update the category with new data 
		categeory.setCateTitle(categotyDto.getCateTitle());
		categeory.setCateDescription(categotyDto.getCateDescription());
		
//		save the new category
		Categeory updateCateg = this.categoryRepo.save(categeory);
		return mapper.map(updateCateg, CategotyDto.class);
	}

	@Override
	public void deleteCategory(Integer cateId) {
		Categeory categeory = this.categoryRepo.findById(cateId)
		.orElseThrow(()-> new ResourceNotFoundException("catagory", "categtitle", cateId));
		
//		detete the categ
		this.categoryRepo.delete(categeory);
		
	}

	//get category
	@Override
	public CategotyDto getCategotyDto(Integer cateId) {
		Categeory categeory = this.categoryRepo.findById(cateId)
				.orElseThrow(()-> new ResourceNotFoundException("category", "categoryid", cateId));
		
		return mapper.map(categeory, CategotyDto.class);
	}

	// get All categories
	@Override
	public List<CategotyDto> getAllCateg() {
		List<Categeory> allCategories = this.categoryRepo.findAll();
		
		List<CategotyDto> categDTOs = allCategories.stream()
				.map(cat -> this.mapper.map(cat, CategotyDto.class))
				.collect(Collectors.toList());
		return categDTOs;
	}

}
