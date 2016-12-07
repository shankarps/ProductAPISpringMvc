package com.shankar.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.shankar.model.Product;

@Service
public class ProductServiceImpl implements ProductService {

    private Map<String,Product> products;
    
    public Map<String, Product> getProducts() {
		return products;
	}

	public void setProducts(Map<String, Product> products) {
		this.products = products;
	}

	@PostConstruct
    public void initData(){
    	products = new HashMap<String, Product>();
    }
    
    @Override
    public List<Product> listAllProducts() {
        return new ArrayList<Product>(products.values());
    }

    private String getNextCode(){
    	int nextCode = 1;
    	if(!products.isEmpty()){
    		nextCode = products.keySet().size()+1;
    	}
    	return ""+nextCode;
    }
    
	@Override
	public Product getProductByCode(String code) {
		return products.get(code);
	}

	@Override
	public Product saveProduct(Product product) {
        if(product.getName() == null){
			throw new RuntimeException("Product name is null");
		}
		product.setCode(getNextCode());
		products.put(product.getCode(), product);

		return product;
	}

	@Override
	public List<Product> getProductByTag(String tag) {
		
		ArrayList<Product> productList = new ArrayList<Product>();  
		
		Set<String> productCodes = products.keySet();
		
		Iterator<String> itr = productCodes.iterator();
		
		while(itr.hasNext()){
			String code = itr.next();
			Product pr = products.get(code);
			if(pr.getTags().contains(tag)){
				productList.add(pr);
			}
		}
		
		return productList;
		
	}

}
