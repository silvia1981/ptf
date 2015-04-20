package com.sg.manager;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;

import com.sg.model.Product;
import com.sg.model.exceptions.ProductNotFoundException;

@RunWith(PowerMockRunner.class)
public class ProductManagerMockedTest {

	private ProductManagerMocked subjectUnderTest;
	
	@Before
	public void init(){
		subjectUnderTest = new ProductManagerMocked();
	}
	
	@Test
	public void testGetProductPartialName(){
		Product product = subjectUnderTest.getProduct("ora", true);
		Assert.assertEquals("Orange", product.getName());
	}
	
	@Test
	public void testGetProductFullName(){
		Product product = subjectUnderTest.getProduct("apple", false);
		Assert.assertEquals("Apple", product.getName());
	}
	
	@Test(expected=ProductNotFoundException.class)
	public void testGetNonExistentProductPartial(){
		subjectUnderTest.getProduct("aaaaa", true);
	}

	@Test(expected=ProductNotFoundException.class)
	public void testGetNonExistentProductFull(){
		subjectUnderTest.getProduct("aaaaa", false);
	}

	@Test
	public void testGetProductById(){
		Product product = subjectUnderTest.getProduct(2);
		Assert.assertEquals("Orange", product.getName());
	}
}
