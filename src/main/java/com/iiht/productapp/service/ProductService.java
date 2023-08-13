package com.iiht.productapp.service;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.iiht.productapp.exception.ProductServiceCustomException;
import com.iiht.productapp.model.Product;
import com.iiht.productapp.model.ProductRequest;
import com.iiht.productapp.model.ProductResponse;

@Service

public interface ProductService {
	 String addProduct(List<ProductRequest> productRequest);

	 ResponseEntity<List<ProductResponse>> getProductByName(String productName) throws ProductServiceCustomException;

	    void reduceQuantity(String productName,long productId, long quantity) throws ProductServiceCustomException;

	    ResponseEntity<String> deleteProductById(String productName,long productId) throws ProductServiceCustomException;

	    List<Product> getAllProduct();

}
