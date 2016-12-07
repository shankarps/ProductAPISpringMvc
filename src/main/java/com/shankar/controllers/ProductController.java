package com.shankar.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.shankar.ProductNameNotProvidedException;
import com.shankar.ProductNotFoundException;
import com.shankar.model.Product;
import com.shankar.service.ProductService;

@RestController
public class ProductController {
	
	private ProductService productService;

	@Autowired 
	public void setProductService(ProductService productService) {
		this.productService = productService;
	}
	
	@RequestMapping(value = {"/products"}, method = RequestMethod.GET,  consumes = MediaType.APPLICATION_JSON_VALUE,
	        produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getAllProducts() {
    	
    	List<Product> products = productService.listAllProducts();
    	return products;
        
    }
	
	@RequestMapping(value = {"/products/{code}"}, method = RequestMethod.GET,  consumes = MediaType.APPLICATION_JSON_VALUE,
	        produces = MediaType.APPLICATION_JSON_VALUE)
    public Product getProductByCode(@PathVariable String code) {
    	Product product = productService.getProductByCode(code);
    	
    	if(product == null){
    		throw new ProductNotFoundException();
    	}
    	
    	return product;
        
    }
    
    @RequestMapping(value = {"/products/tag/{tag}"}, method = RequestMethod.GET,  consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Product> getProductByTag(@PathVariable String tag) {
    	
    	List<Product> products = productService.getProductByTag(tag);
    	System.out.println(products);
    	return products;
        
    }
    
    @RequestMapping(value = {"/products"}, method = RequestMethod.POST,  consumes = MediaType.APPLICATION_JSON_VALUE,
    	    produces = MediaType.APPLICATION_JSON_VALUE)
    public Product saveProduct(@RequestBody Product product) {
		if(product == null || product.getName() == null || product.getName().equals("") ){
			throw new ProductNameNotProvidedException();
		}
		Product newProduct = productService.saveProduct(product);
		return newProduct;
		
	}
}
