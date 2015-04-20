package com.sg.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.powermock.modules.junit4.PowerMockRunner;

import com.sg.controller.ShoppingCartService;
import com.sg.manager.interfaces.OfferManager;
import com.sg.manager.interfaces.ProductManager;
import com.sg.model.Bill;
import com.sg.model.Offer;
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
		
		List<Offer> offers = new ArrayList<Offer>();
		Offer offer1 = new Offer();
		offer1.getBilledProducts().add(a);
		offer1.getBilledProducts().add(a);
		offer1.getProducts().add(b);
		//if you buy a b, you will be billed for 2a
		offers.add(offer1);
		
		OfferManager mockedOfferManager = Mockito.mock(OfferManager.class);
		Mockito.when(mockedOfferManager.getOffers()).thenReturn(offers);
		Mockito.when(mockedOfferManager.offerMatch(Mockito.eq(offer1), Mockito.anyMap())).thenAnswer(new Answer<Boolean>() {
			private boolean called = false;
			@Override
			public Boolean answer(InvocationOnMock invocation) throws Throwable {
				if(!called){
					called = true;
					return true;
				} else {
					return false;
				}
			}
		});		
		
		ShoppingCartService subjectUnderTest = new ShoppingCartService();
		subjectUnderTest.setCalculator(new CalculatorServiceImpl());
		
		((CalculatorServiceImpl)subjectUnderTest.getCalculator()).setOffersManager(mockedOfferManager);
		((CalculatorServiceImpl)subjectUnderTest.getCalculator()).setProductManager(mockedProductManager);
		
		Bill bill = subjectUnderTest.service("a,b,b,a,b", true);
		
		Assert.assertEquals("The amount is not correct", "8.62", bill.getTotal().getValueAsString());
		Mockito.verify(mockedOfferManager).applyOffer(offer1, bill);
	}
}
