package com.shankar;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shankar.controllers.ProductController;
import com.shankar.model.Product;
import com.shankar.service.ProductService;

/*
 * This Test class is for testing the Controller.
 * */
@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

	@Autowired
	private ProductController productController;

	@MockBean
	private ProductService productService;

	private Product testProduct;
	
    @Autowired
    private MockMvc mvc;
    
    @Autowired
    ObjectMapper objectMapper;

	@Before
	public void initPorduct() {
		testProduct = new Product();
		testProduct.setCode("1");
		testProduct.setPrice(120.00d);
		testProduct.setName("Test 1");
		Set<String> tags = new HashSet<String>();
		tags.add("computer");
		testProduct.setTags(tags);
	}
	
	   @Test
	    public void testGetProductByCode() throws Exception {
			given(this.productService.getProductByCode("1"))
			.willReturn(testProduct);
			
	        this.mvc.perform(get("/products/1").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
	                .andExpect(status().isOk()).andExpect(content().string(containsString("Test 1")));
	    }

	@Test
	public void testSaveProduct() throws Exception {
		// Create new Product
		Product product = new Product();
		product.setName("Test 2");
		product.setPrice(123.21d);
		Set<String> tags = new HashSet<String>();
		tags.add("hardware");
		product.setTags(tags);
		
		given(this.productService.saveProduct(product)).willReturn(
				product);

		this.mvc.perform(post("/products").content(objectMapper.writeValueAsString(product)).accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(content().string(containsString("Test 2")));
		
	}
	
	@Test
	public void testSaveProductValidation() throws Exception {

		// new Product without name
		Product product = new Product();
		product.setPrice(123.21d);
		Set<String> tags = new HashSet<String>();
		tags.add("hardware");
		product.setTags(tags);

		this.mvc.perform(post("/products").content(objectMapper.writeValueAsString(product))
			.accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

	}
	
	
	
	@Test
	public void testGetProductByInvalidCode() throws Exception {
		given(this.productService.getProductByCode("1"))
				.willReturn(testProduct);

		 this.mvc.perform(get("/products/INVALID").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
         .andExpect(status().isNotFound());

	}

	
	@Test
	public void testGetProductByTag() throws Exception {
		ArrayList<Product> products = new ArrayList<Product>();
		products.add(testProduct);
		given(this.productService.getProductByTag("computer")).willReturn(
				products);

		this.mvc.perform(get("/products/tag/computer").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk());

	}
	
	@Test
	public void testGetAllProducts() throws Exception {
		ArrayList<Product> products = new ArrayList<Product>();
		products.add(testProduct);
		given(this.productService.listAllProducts()).willReturn(
				products);

		this.mvc.perform(get("/products").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andExpect(content().string(containsString("Test 1")));

	}

}
