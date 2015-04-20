package com.sg.model;

import java.util.ArrayList;
import java.util.List;

public class Offer implements Billable, Comparable<Billable>{
	private List<Product> products;
	private List<Product> billedProducts;
	private String name;
	
	public Offer(){
		products = new ArrayList<Product>();
		billedProducts = new ArrayList<Product>();
	}
	
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public List<Product> getBilledProducts() {
		return billedProducts;
	}
	public void setBilledProducts(List<Product> billedProducts) {
		this.billedProducts = billedProducts;
	}
	
	@Override
	public String toString() {
		
		return "Offer, you have "+products+" paying for "+billedProducts;
	}

	public String getName() {
		if(name==null){
			return toString();
		} else {
			return name;
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public float getCost() {
		float cost = 0;
		for (Product product : billedProducts) {
			cost += product.getCost();
		}
		return cost;
	}

	@Override
	public int compareTo(Billable paramT) {
		return paramT.getName().compareTo(getName());
	}
	
}
