package com.sg.manager.interfaces;

import java.util.List;
import java.util.Map;

import com.sg.model.Bill;
import com.sg.model.Billable;
import com.sg.model.Offer;
import com.sg.model.Product;

public interface OfferManager {

	boolean offerMatch(Offer offer, Map<Billable, Integer> productsAmount);
	void applyOffer(Offer offer, Bill bill);
	List<Offer> getOffers();	
	
}
