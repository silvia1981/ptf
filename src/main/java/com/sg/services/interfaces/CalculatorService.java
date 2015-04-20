package com.sg.services.interfaces;

import com.sg.model.Bill;

public interface CalculatorService {

	Bill calculatePrice(String[] products, boolean partialMatching);
	
}
