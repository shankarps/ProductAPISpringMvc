package com.shankar;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.shankar.model.Product;
import com.shankar.service.ProductService;

/*
 * This Test class is for testing the Controller.
 * */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductServiceTest {

	@Autowired
	private ProductService productService;

	private Product testProduct;

	@Before
	public void initPorduct() {
		
		//Save one product entity for testing
		testProduct = new Product();
		testProduct.setPrice(120.00d);
		testProduct.setName("Test 1");
		Set<String> tags = new HashSet<String>();
		tags.add("computer");
		testProduct.setTags(tags);
		
		testProduct = productService.saveProduct(testProduct);
		
	}

	@Test
	public void testSaveProduct() throws Exception {
		// Create new Product
		Product product = new Product();
		product.setName("Test 1");
		product.setPrice(123.21d);
		Set<String> tags = new HashSet<String>();
		tags.add("hardware");
		product.setTags(tags);

		Product savedProduct = productService.saveProduct(product);
		assertNotNull(savedProduct);
		assertTrue(savedProduct.getPrice().equals(123.21d));
		assertTrue(savedProduct.getName().equals("Test 1"));
	}

	@Test(expected=RuntimeException.class)
	public void testSaveProductValidation() throws Exception {

		// new Product without name
		Product product = new Product();
		product.setPrice(123.21d);
		Set<String> tags = new HashSet<String>();
		tags.add("hardware");
		product.setTags(tags);

		productService.saveProduct(product);
		

	}

	@Test
	public void testGetProductByCode() throws Exception {

		Product product = productService.getProductByCode(testProduct.getCode());
		assertNotNull(product);
		assertTrue(product.getPrice().equals(testProduct.getPrice()));
		assertTrue(product.getName().equals(testProduct.getName()));

	}

	@Test
	public void testGetProductByInvalidCode() throws Exception {
		Product product = productService.getProductByCode("INVALID");
		assertNull(product);

	}

	@Test
	public void testGetProductByTag() throws Exception {

		List<Product> products = productService.getProductByTag("computer");
		assertNotNull(products);
		assertTrue(products.size() > 0);

	}

	@Test
	public void testGetAllProducts() throws Exception {

		List<Product> products = productService.listAllProducts();
		assertNotNull(products);
		assertTrue(products.size() > 0);

	}
	
}
