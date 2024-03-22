package org.jsp.ecommerceapp.dto;

import lombok.Data;

@Data
public class ResponseStructure<T> {
	private String message;
	private T data;
	private int statuscode;

}