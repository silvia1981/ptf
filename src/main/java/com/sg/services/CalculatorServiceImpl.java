package com.sg.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sg.manager.interfaces.ProductManager;
import com.sg.model.Bill;
import com.sg.model.Product;
import com.sg.services.interfaces.CalculatorService;

/**
 * This is a service to calculate the values for a list of items
 * 
 * @author sgomez
 *
 */
@Service
public class CalculatorServiceImpl implements CalculatorService {

	@Autowired
	private ProductManager productManager;
	
	@Override
	public Bill calculatePrice(String[] products, boolean partialMatch) {
		
		Bill bill = new  Bill();
		
		for (String string : products) {
			Product product = productManager.getProduct(string, partialMatch);
			bill.addProduct(product);
		}
		
		return bill;
	}

	public ProductManager getProductManager() {
		return productManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

}
