package com.sg.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sg.manager.interfaces.OfferManager;
import com.sg.manager.interfaces.ProductManager;
import com.sg.model.Bill;
import com.sg.model.Billable;
import com.sg.model.Offer;
import com.sg.model.Product;

@Service
public class OfferManagerMocked implements OfferManager {

	// the cross dependency between managers, will be eliminated if the data
	// cames from
	// a real data source
	@Autowired
	private ProductManager productManager;

	private List<Offer> mockedOffers = null;

	@Override
	public boolean offerMatch(Offer offer, Map<Billable, Integer> productsAmount) {
		initOffers();
		Map<Billable, Integer> remainingProducts = copyProductsMap(productsAmount);
		List<Product> products = copyList(offer.getProducts());
		
		while(products.size()>=1 && remainingProducts.containsKey(products.get(0)) 
				&& remainingProducts.get(products.get(0))>0){
			remainingProducts.put(products.get(0), remainingProducts.get(products.get(0))-1);
			products.remove(0);
		}
		
		return products.size()==0;
	}

	private Map<Billable, Integer> copyProductsMap(
			Map<Billable, Integer> productsAmount) {
		
		HashMap<Billable, Integer> newMap = new HashMap<Billable, Integer>();
		for (Entry<Billable, Integer> prodAmount : productsAmount.entrySet()) {
			newMap.put(prodAmount.getKey(), prodAmount.getValue());
		}
		return newMap;
	}

	@Override
	public void applyOffer(Offer offer,
			Bill bill) {
		initOffers();
		//1st remove products and price payed from the bill
		List<Product> products = copyList(offer.getProducts());
		
		while(products.size()>0){
			Billable removedProduct = products.get(products.size()-1);
			bill.getProducts().put(removedProduct, bill.getProducts().get(removedProduct)-1);
			bill.getTotal().substract(removedProduct.getCost());
			products.remove(removedProduct);
		}
			
		//then add the offer to the bill, and add the price for the offer
		bill.addBillable(offer);
	}

	private void initOffers() {
		if(mockedOffers==null){
			
			mockedOffers = new ArrayList<Offer>() {
				{
					Product apple = productManager.getProduct("apple", false);
					Product orange = productManager.getProduct("orange", false);
					
					Offer offer1 = new Offer();
					offer1.getBilledProducts().add(apple);
					offer1.getProducts().add(apple);
					offer1.getProducts().add(apple);
					
					Offer offer2 = new Offer();
					offer2.getBilledProducts().add(orange);
					offer2.getBilledProducts().add(orange);
					offer2.getProducts().add(orange);
					offer2.getProducts().add(orange);
					offer2.getProducts().add(orange);
					
					add(offer1);
					add(offer2);
				}
			};
		}
	}

	private <T> List<T> copyList(List<T> source){
		ArrayList<T> copied = new ArrayList<T>();
		for (T t : source) {
			copied.add(t);
		}
		return copied;
	}
	
	@Override
	public List<Offer> getOffers() {
		initOffers();
		return mockedOffers;
	}

	public ProductManager getProductManager() {
		return productManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public void setMockedOffers(List<Offer> mockedOffers) {
		this.mockedOffers = mockedOffers;
	}

}
