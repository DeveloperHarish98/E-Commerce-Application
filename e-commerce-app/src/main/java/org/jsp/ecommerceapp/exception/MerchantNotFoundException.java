package org.jsp.ecommerceapp.exception;

public class MerchantNotFoundException extends RuntimeException {
	@Override

	public String getMessage() {
		return "Merchant Not Found";
	}
}
