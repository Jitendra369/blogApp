package com.blogapp.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blogapp.impl.CategServiceImple;
import com.blogapp.payload.APIResponse;
import com.blogapp.payload.CategotyDto;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {

	@Autowired
	private CategServiceImple categServiceImple;
	
	
//	POST
	@PostMapping("/")
	public ResponseEntity<CategotyDto> saveCategory( @Valid @RequestBody CategotyDto categotyDto) {
		CategotyDto saveCategotyDto = this.categServiceImple.saveCategotyDto(categotyDto);
		return new ResponseEntity<CategotyDto>(saveCategotyDto, HttpStatus.CREATED);
	}
	
	
//	PUT
	@PutMapping("/{catId}")
	public ResponseEntity<CategotyDto> updateCategDto(
			@Valid @RequestBody CategotyDto categotyDto,
			@PathVariable("catId") Integer catId){
		
		CategotyDto updateCategotyDto = this.categServiceImple.updateCategotyDto(categotyDto, catId);
		return new ResponseEntity<CategotyDto>(updateCategotyDto, HttpStatus.OK);
	}
	
//	GET
	@GetMapping("/{catId}")
	public ResponseEntity<CategotyDto> getCategory(@PathVariable("catId") Integer catId){
		CategotyDto categotyDto = this.categServiceImple.getCategotyDto(catId);
		return new ResponseEntity<CategotyDto>(categotyDto,HttpStatus.OK);
	}
	
//	GET-ALL
	@GetMapping("/all")
	public ResponseEntity<List<CategotyDto>> getAllCateg(){
		List<CategotyDto> allCateg = this.categServiceImple.getAllCateg();
		return new ResponseEntity<List<CategotyDto>>(allCateg, HttpStatus.OK);
	}
	
//	DELETE
	@DeleteMapping("/{catId}")
	public ResponseEntity<APIResponse> deleteCateg(@PathVariable("catId") Integer catId){
		this.categServiceImple.deleteCategory(catId);
		return ResponseEntity.ok(new APIResponse("Category deleted",HttpStatus.OK, true));
	}
}
