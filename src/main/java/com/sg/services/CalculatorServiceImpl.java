package com.sg.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sg.manager.interfaces.OfferManager;
import com.sg.manager.interfaces.ProductManager;
import com.sg.model.Bill;
import com.sg.model.Billable;
import com.sg.model.Offer;
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
	
	@Autowired
	private OfferManager offersManager;
	
	@Override
	public Bill calculatePrice(String[] products, boolean partialMatch) {
		
		Bill bill = new  Bill();
		
		for (String string : products) {
			Product product = productManager.getProduct(string, partialMatch);
			bill.addBillable(product);
		}
		
		//calculate the offers
		List<Offer> offers = offersManager.getOffers();
		
		for (Offer offer : offers) {
			while(offersManager.offerMatch(offer, bill.getProducts())){
				offersManager.applyOffer(offer, bill);
			}
		}
		
		List<Billable> itemsToRemove = new ArrayList<Billable>();
		//remove emtpy products from bill
		for (Map.Entry<Billable, Integer> item : bill.getProducts().entrySet()) {
			if(item.getValue()==0){
				itemsToRemove.add(item.getKey());
			}
		}
		
		for (Billable billable : itemsToRemove) {
			bill.getProducts().remove(billable);
		}
		return bill;
	}

	public ProductManager getProductManager() {
		return productManager;
	}

	public void setProductManager(ProductManager productManager) {
		this.productManager = productManager;
	}

	public OfferManager getOffersManager() {
		return offersManager;
	}

	public void setOffersManager(OfferManager offersManager) {
		this.offersManager = offersManager;
	}

}
