package com.blogapp.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blogapp.payload.APIResponse;

import jakarta.validation.ConstraintViolationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<APIResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException exception){
		String message = exception.getMessage();
		APIResponse apiResponse = new APIResponse(message,HttpStatus.NOT_FOUND, false);
		return new ResponseEntity<APIResponse>(apiResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArugNotFoundExc(
				MethodArgumentNotValidException exception
			){
		
		HashMap<String, String> resp = new HashMap<>();
		exception.getBindingResult().getAllErrors().forEach((error)->{
			String field = ((FieldError)error).getField();
			String message = error.getDefaultMessage();
		
			resp.put(field, message);
		});
		
		return new ResponseEntity<Map<String,String>>(resp, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Map<String, String>> handleConstainerEntity(
			ConstraintViolationException exception
			){
		HashMap<String, String> response = new HashMap<>();
		exception.getConstraintViolations().forEach((error)->{
			response.put((String) error.getRootBean(), error.getMessage());
		});
		
		return new ResponseEntity<Map<String,String>>(response, HttpStatus.BAD_REQUEST);
	}
}
