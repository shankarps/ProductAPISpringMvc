package com.shankar.service;

import java.util.List;

import com.shankar.model.Product;

public interface ProductService {

    List<Product> listAllProducts();

    Product getProductByCode(String code);

    Product saveProduct(Product product);

	List<Product> getProductByTag(String tag);
}