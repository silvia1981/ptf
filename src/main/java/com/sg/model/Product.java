package com.sg.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * This is an selleable product
 * 
 * @author sgomez
 *
 */
public class Product implements Comparable<Product>{

	private String name;
	private int code;
	private float cost;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public float getCost() {
		return cost;
	}
	public void setCost(float cost) {
		this.cost = cost;
	}
	
	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, false);
	}
	
	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(obj, this, false);
	}
	@Override
	public String toString() {
		return name;
	}
	@Override
	public int compareTo(Product paramT) {
		return this.getName().compareTo(paramT.getName());
	}
}
