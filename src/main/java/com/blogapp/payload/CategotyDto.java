package com.blogapp.payload;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategotyDto {

	
	private Integer cateId;
	
	@NotBlank
	@Size(min = 4, message = "min size must be 4 characters")
	private String cateTitle;
	
	@NotBlank
	@Size(min = 10, message = "min size must be 10")
	private String cateDescription;
}
