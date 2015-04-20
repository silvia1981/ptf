package com.sg.service;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.sg.controller.ShoppingCartService;
import com.sg.manager.interfaces.ProductManager;
import com.sg.model.Bill;
import com.sg.model.Product;
import com.sg.services.CalculatorServiceImpl;

@RunWith(PowerMockRunner.class)
public class ShoppingCartServiceTest {
	
	/**
	 * This test test the service AND the calculator, is an integration test
	 * This was made in this way due the service is skinny class
	 */
	@Test
	public void testWebService(){
		Product a = new Product();
		a.setCost(1.01f);
		a.setName("");
		
		Product b = new Product();
		b.setCost(2.2f);
		b.setName("");
		
		ProductManager mockedProductManager = Mockito.mock(ProductManager.class);
		Mockito.when(mockedProductManager.getProduct(Mockito.eq("a"), Mockito.anyBoolean()))
			.thenReturn(a);
		Mockito.when(mockedProductManager.getProduct(Mockito.eq("b"), Mockito.anyBoolean()))
			.thenReturn(b);
		
		
		ShoppingCartService subjectUnderTest = new ShoppingCartService();
		subjectUnderTest.setCalculator(new CalculatorServiceImpl());
		
		((CalculatorServiceImpl)subjectUnderTest.getCalculator()).setProductManager(mockedProductManager);
		
		Bill bill = subjectUnderTest.service("a,b,b,a,b", true);
		
		Assert.assertEquals("The amount is not correct", "8.62", bill.getTotal().getValueAsString());
		
	}
}
