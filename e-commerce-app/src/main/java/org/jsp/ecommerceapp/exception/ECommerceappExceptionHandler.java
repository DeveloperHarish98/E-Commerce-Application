package org.jsp.ecommerceapp.exception;


import org.jsp.ecommerceapp.dto.ResponseStructure;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ECommerceappExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleUNFE(UserNotFoundException exception) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		structure.setMessage(exception.getMessage());
		structure.setStatuscode(HttpStatus.NOT_FOUND.value());
		structure.setData("User not found");
		return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(IdNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleINFE(IdNotFoundException exception) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		structure.setData("Cannot Find ID");
		structure.setMessage(exception.getMessage());
		structure.setStatuscode(HttpStatus.NOT_FOUND.value());

		return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(InvalidCredentialException.class)
	public ResponseEntity<ResponseStructure<String>> handleICE(InvalidCredentialException exception) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		structure.setData("Invalid Credential");
		structure.setMessage(exception.getMessage());
		structure.setStatuscode(HttpStatus.NOT_FOUND.value());

		return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(ProductNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handlePNFE(ProductNotFoundException exception) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		structure.setData("Product Not Found");
		structure.setMessage(exception.getMessage());
		structure.setStatuscode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.NOT_FOUND);
	}
	
	
	@ExceptionHandler(IllegalStateException.class)
	public ResponseEntity<ResponseStructure<String>> handleISE(IllegalStateException exception) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		structure.setData("Account is not active");
		structure.setMessage(exception.getMessage());
		structure.setStatuscode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(AddressNotFoundException.class)
	public ResponseEntity<ResponseStructure<String>> handleANFE(AddressNotFoundException exception) {
		ResponseStructure<String> structure = new ResponseStructure<>();
		structure.setData("Address Not Found");
		structure.setMessage(exception.getMessage());
		structure.setStatuscode(HttpStatus.NOT_FOUND.value());
		return new ResponseEntity<ResponseStructure<String>>(structure, HttpStatus.NOT_FOUND);
	}
	
}