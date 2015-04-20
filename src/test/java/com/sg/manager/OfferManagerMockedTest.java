package com.sg.manager;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.modules.junit4.PowerMockRunner;

import com.sg.manager.interfaces.OfferManager;
import com.sg.manager.interfaces.ProductManager;
import com.sg.model.Bill;
import com.sg.model.Offer;
import com.sg.model.Product;

@RunWith(PowerMockRunner.class)
public class OfferManagerMockedTest {

	private OfferManager offerManager;
	private Bill testBill;
	private Offer offer1;
	
	@Before
	public void createObjects(){
		offerManager = new OfferManagerMocked();
		
		Product a = new Product();
		a.setCost(2f);
		a.setName("a");
		
		Product b = new Product();
		b.setCost(7f);
		b.setName("b");
		
		ProductManager mockedProductManager = Mockito.mock(ProductManager.class);
		Mockito.when(mockedProductManager.getProduct(Mockito.eq("a"), Mockito.anyBoolean()))
			.thenReturn(a);
		Mockito.when(mockedProductManager.getProduct(Mockito.eq("b"), Mockito.anyBoolean()))
			.thenReturn(b);
		
		((OfferManagerMocked)offerManager).setProductManager(mockedProductManager);
		
		//mock offers
		List<Offer> offers = new ArrayList<Offer>();
		offer1 = new Offer();
		offer1.getBilledProducts().add(a);
		offer1.getBilledProducts().add(a);
		offer1.getProducts().add(b);
		offer1.getProducts().add(b);
		offer1.getProducts().add(b);
		//if you buy a 3b, you will be billed for 2a
		offers.add(offer1);
		
		((OfferManagerMocked)offerManager).setMockedOffers(offers);
		
		testBill = new Bill();
		testBill.addBillable(a);
		testBill.addBillable(a);
		testBill.addBillable(a);
		testBill.addBillable(a);
		testBill.addBillable(b);
		testBill.addBillable(b);
		testBill.addBillable(b);
		testBill.addBillable(b);
		
	}
	
	@Test
	public void testOffersDetect(){
		Assert.assertTrue("The offer wasn't detected", offerManager.offerMatch(offer1, testBill.getProducts()));
	}

	@Test
	public void testOfferApply(){
		//without offer, 4*a + 4*b = 2*4 + 7*4 = 8 + 28 = 36
		Assert.assertEquals("The total amount is not correct", testBill.getTotal().getValueAsString(), "36.00");

		offerManager.applyOffer(offer1, testBill);
		//with offer, will be 6*a + b = 12 + 7 = 19
		Assert.assertEquals("The offer was not applied", testBill.getTotal().getValueAsString(), "19.00");
	}

}
