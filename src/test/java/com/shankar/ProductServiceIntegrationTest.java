package com.shankar;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import com.shankar.model.Product;
/*
 * This Test class is for integration testing the Product API.
 * 
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ProductServiceIntegrationTest {

	@Autowired
	private TestRestTemplate restTemplate;

	private Product newPr;

	@Before
	public void saveTestroduct() {
		// Create a Test Product.
		Product product = new Product();
		product.setName("Test Product");
		product.setPrice(100.00d);
		Set<String> tags = new HashSet<String>();
		tags.add("computer");
		product.setTags(tags);

		HttpEntity<Product> request = new HttpEntity<Product>(product);

		ResponseEntity<Product> response = this.restTemplate.postForEntity(
				"/products", request, Product.class);
		newPr = response.getBody();
	}

	@Test
	public void testSaveProduct() {

		// Create new Product
		Product product = new Product();
		product.setName("Test 1");
		product.setPrice(123.21d);
		Set<String> tags = new HashSet<String>();
		tags.add("hardware");
		product.setTags(tags);

		HttpEntity<Product> request = new HttpEntity<Product>(product);

		ResponseEntity<Product> response = this.restTemplate.postForEntity(
				"/products", request, Product.class);
		Product pr = response.getBody();
		
		assertTrue(response.getStatusCode().is2xxSuccessful());
		assertThat(pr, notNullValue());
		assertThat(pr.getName(), is("Test 1"));
		assertThat(pr.getPrice(), is(123.21d));
		
	}
	
	@Test
	public void testSaveProductValidation() {

		// Create new Product
		Product product = new Product();
		product.setPrice(123.21d);
		Set<String> tags = new HashSet<String>();
		tags.add("hardware");
		product.setTags(tags);

		HttpEntity<Product> request = new HttpEntity<Product>(product);

		ResponseEntity<Product> response = this.restTemplate.postForEntity(
				"/products", request, Product.class);
		assertTrue(response.getStatusCode().is4xxClientError());
	}

	@Test
	public void testGetProductByInvalidCode() {
		// Get products by code
		ResponseEntity<Product> response = restTemplate.getForEntity(
				"/products/" + "INVALID", Product.class);

		assertTrue(response.getStatusCode().is4xxClientError());
	}
	
	@Test
	public void testGetProductByCode() {
		// Get products by code
		ResponseEntity<Product> response = restTemplate.getForEntity(
				"/products/" + newPr.getCode(), Product.class);

		assertTrue(response.getStatusCode().is2xxSuccessful());
		Product pr = response.getBody();
		assertThat(pr, notNullValue());
		assertThat(pr.getName(), is("Test Product"));
		assertThat(pr.getPrice(), is(100.00d));

	}

	@Test
	public void testGetProductByTag() {
		// Get products for Tag
		ResponseEntity<ArrayList> productListResp = restTemplate.getForEntity(
				"/products/tag/computer", ArrayList.class);

		assertTrue(productListResp.getStatusCode().is2xxSuccessful());
		ArrayList productList = productListResp.getBody();
		assertThat(productList, notNullValue());
		assertTrue(productList.size() > 0);

	}

	@Test
	public void testGetAllProducts() {

		// Get all products
		ResponseEntity<ArrayList> productListResp = restTemplate.getForEntity(
				"/products", ArrayList.class);

		assertTrue(productListResp.getStatusCode().is2xxSuccessful());
		ArrayList productList = productListResp.getBody();
		assertThat(productList, notNullValue());
		// assertThat(productList.size(), is(1));
		assertThat(productList.size(), greaterThan(0));
	}

}
