package org.jsp.ecommerceapp.exception;

public class UserNotFoundException extends RuntimeException {
	@Override
	public String getMessage() {
		return "User Not Found";
	}
}