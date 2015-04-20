package com.sg.manager;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.Predicate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.sg.manager.interfaces.ProductManager;
import com.sg.model.Product;
import com.sg.model.exceptions.ProductNotFoundException;

/**
 * This should be replaced for a connection to somewhere (probably a database,
 * gigaspaces, web service, etc) to get the prices and promotions, as this is
 * just a demo project, that steep will be ommited in order to be able to run
 * this without habing to install database, GS, etc
 * 
 * The filters implemented here probably can be made by the underlying system
 * 
 * @author sgomez
 *
 */
@Service
public class ProductManagerMocked implements ProductManager {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(ProductManagerMocked.class);

	private final Set<Product> mockedProducts = new HashSet<Product>() {

		private static final long serialVersionUID = -36326040542395365L;
		{
			Product p = new Product();
			p.setName("Apple");
			p.setCode(1);
			p.setCost(0.6f);
			add(p);

			p = new Product();
			p.setName("Orange");
			p.setCode(2);
			p.setCost(0.25f);
			add(p);
		}
	};

	@Override
	public Product getProduct(final String name, final boolean partialMatch) {
		LOGGER.debug(
				"Trying to get the product by name {} using partial matching {} ",
				name, partialMatch);
		return getSingleProduct(new Predicate<Product>() {
			@Override
			public boolean evaluate(Product arg0) {
				String lowerName = arg0.getName().toLowerCase();
				boolean matched;
				if (partialMatch) {
					matched = lowerName.contains(name.toLowerCase());
				} else {
					matched = lowerName.equals(name);
				}
				LOGGER.trace("Product {} matched {}", arg0, matched);
				return matched;
			}
		});
	}

	@Override
	public Product getProduct(final int code) {
		return getSingleProduct(new Predicate<Product>() {
			@Override
			public boolean evaluate(Product arg0) {
				return arg0.getCode() == code;// TODO Auto-generated method stub
			}

		});
	}

	private Product getSingleProduct(Predicate<Product> filter) {
		Collection<Product> products = CollectionUtils.select(mockedProducts,
				filter);
		if (products.size() == 1) {
			return products.iterator().next();
		} else {
			throw new ProductNotFoundException(
					"The product were not found or there is more "
							+ "than 1 product matching (" + products + ")");
		}

	}
}
