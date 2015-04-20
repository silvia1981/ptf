package com.sg.model;

import java.util.Map;
import java.util.TreeMap;

/**
 * This is interded as DTO for exposing the result of the calculation of billing
 * This contain a map, with the amount of items by kind, and the total cost of all the intems
 * 
 * @author sgomez
 *
 */
public class Bill {

	/**
	 * This class is to reduce the rounding problems of float numbers
	 * Other already implemented classes can be used instead of this one, but due the simplicity
	 * of the operations, this was implemented here 
	 * 
	 * @author sgomez
	 *
	 */
	public static class AmountOfMoney {
		private int cents;
		public AmountOfMoney() {
			cents = 0;
		}
		
		@Override
		public String toString() {
			String centsString = ""+cents%100;
			if(centsString.length()==1){
				centsString = "0"+centsString;
			}
			return ""+cents/100+"."+centsString;
		}
		
		public void add(float value){
			cents+= value*100;
		}
		
		public String getValueAsString(){
			return toString();
		}
	}
	
	private Map<Product, Integer> products;
	private AmountOfMoney total;
	
	public Bill() {
		products = new TreeMap<Product, Integer>();
		total = new AmountOfMoney();
	}
	
	public synchronized void  addProduct(Product p){
		int amountOfProducts = 1;
		if(products.containsKey(p)){
			amountOfProducts += products.get(p);
		}
		products.put(p, amountOfProducts);
		total.add(p.getCost());
	}

	public AmountOfMoney getTotal() {
		return total;
	}

	public Map<Product, Integer> getProducts() {
		return products;
	}
	
}
