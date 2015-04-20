package com.sg.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.sg.model.Bill;
import com.sg.services.interfaces.CalculatorService;

/**
 * Main controller for processing requests
 * 
 * @author sgomez
 *
 */
@Controller
public class ShoppingCartService {

	private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartService.class);
	
	@Autowired
	private CalculatorService calculator;
	private static final String PRODUCT_SEPARATOR = ",";
	
	/**
	 * This method is the facade for the service, the service is exposed as rest, with the url
	 * ./getPath/{usePartialNameMatching}/products.
	 * The service respond a json object
	 * If a product is not found, the service respond http code 500
	 * 
	 * @param products List of products, separated by "," character
	 * @param usePartialNameMatching if true, try to match the products using partial matching, if false
	 * 					it will search for exactly the same name for products
	 * 
	 * 
	 * @return json string indicating, how many of each objects were added, and the total amount
	 */
	@RequestMapping(value = "/getCost/{usePartialNameMatching}/{products}")
	public @ResponseBody Bill service(@PathVariable String products, @PathVariable boolean usePartialNameMatching){
		String[] productList = products.split(PRODUCT_SEPARATOR);
		
		LOGGER.debug("request with atts {}",productList);
		
		return calculator.calculatePrice(productList, usePartialNameMatching);	
	}

	public CalculatorService getCalculator() {
		return calculator;
	}

	public void setCalculator(CalculatorService calculator) {
		this.calculator = calculator;
	}

}
