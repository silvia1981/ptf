package com.sg.model.exceptions;

public class ProductNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 3194188262484616734L;

	public ProductNotFoundException(String error) {
		super(error);
	}
}
