package com.sg.manager.interfaces;

import com.sg.model.Product;
import com.sg.model.exceptions.ProductNotFoundException;

public interface ProductManager {

	/**
	 * Get product by name, if more than one product is found, or none, it throws ProductNotFoundException
	 *
	 * @param name filter for seach the product
	 * @param partialMatch if true, it search using partial matching
	 * @return  The found product
	 * @throws ProductNotFoundException
	 */
	Product getProduct(String name, boolean partialMatch) throws ProductNotFoundException;
	
	/**
	 * Get the product by code, if more than one product is found, or none, it throws ProductNotFoundException
	 * 
	 * @param code product code (this can be used to put the barcode of the product)
	
	 * @return The found product
	 * @throws ProductNotFoundException
	 */
	Product getProduct(int code) throws ProductNotFoundException;
	
	
	
}
