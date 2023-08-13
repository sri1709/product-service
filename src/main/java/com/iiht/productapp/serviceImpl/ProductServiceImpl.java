package com.iiht.productapp.serviceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.hibernate.mapping.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.iiht.productapp.exception.ProductServiceCustomException;

import com.iiht.productapp.model.ResponseMessage;
import com.iiht.productapp.model.ProductResponse;
import com.iiht.productapp.model.Product;
import com.iiht.productapp.model.ProductRequest;
import com.iiht.productapp.model.UserData;
import com.iiht.productapp.repository.ProductRepository;
import com.iiht.productapp.repository.UserRepository;
import com.iiht.productapp.service.ProductService;


import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service

@Log4j2
public class ProductServiceImpl implements ProductService{
	
	@Autowired
	private ProductRepository productRepository;

    @Override
    public String addProduct(List<ProductRequest> productRequest) {
        log.info("ProductServiceImpl | addProduct is called");

        List<Product> productList=new ArrayList<> ();
        productRequest.stream().forEach(resp->{
        	Product product=new Product();
        	product.setProductName(resp.getProductName());
        	product.setPrice(resp.getPrice());
        	product.setQuantity(resp.getQuantity());
        	product.setFeatures(resp.getFeatures());
        	product.setProductDescription(resp.getProductDescription());
        	if(resp.getQuantity()==0) {
        	product.setProductStatus("OUT OF STOCK");
        	}else {
        		product.setProductStatus("HURRY UP TO PURCHASE");
        	}
        	productList.add(product);
        });

        
        
         productRepository.saveAll(productList).forEach(productList::add);

        log.info("ProductServiceImpl | addProduct | Product Created");
        log.info("ProductServiceImpl | addProduct | Product Id : " + productList.get(0).getProductId());
        return "product added successfully";
    }

    @Override
    public ResponseEntity<List<ProductResponse>> getProductByName(String productName) throws ProductServiceCustomException {

        log.info("ProductServiceImpl | getProductById is called");
        log.info("ProductServiceImpl | getProductById | Get the product for productId: {}", productName);

        List<Product> product=new ArrayList<>();
            productRepository.findByproductName(productName).forEach(product::add);
            log.info("ProductServiceImpl | getProductById | productResponse :" + product.toString());

            if(product.stream().collect(Collectors.toList()).isEmpty()) {
               throw new ProductServiceCustomException("Product with given Id not found","PRODUCT_NOT_FOUND");
          }
          
          
          List<ProductResponse> productResponseList=new ArrayList<>();
          product.stream().forEach(resp->{
        	  ProductResponse productResponse=new ProductResponse();
        	  productResponse.setProductId(resp.getProductId());
        	  productResponse.setProductName(resp.getProductName());
        	  productResponse.setPrice(resp.getPrice());
        	  productResponse.setQuantity(resp.getQuantity());
        	  productResponse.setFeatures(resp.getFeatures());
        	  productResponse.setProductDescription(resp.getProductDescription());
        	  productResponseList.add(productResponse);
          });  
       
          log.info("ProductServiceImpl | getProductById | productResponse :" + productResponseList.toString());

       return new ResponseEntity<List<ProductResponse>>(productResponseList,HttpStatus.OK);
         
          }

    @Override
    public void reduceQuantity(String productName, long productId, long quantity) throws ProductServiceCustomException {

        log.info("Reduce Quantity {} for Id: {}", quantity,productId);

        Product product
                = productRepository.findById(productId)
                .orElseThrow(() -> new ProductServiceCustomException(
                        "Product with given Id not found",
                        "PRODUCT_NOT_FOUND"
                ));
        if(product.getProductName().equalsIgnoreCase(productName)) {

        if(product.getQuantity() < quantity) {
            throw new ProductServiceCustomException(
                    "Product does not have sufficient Quantity",
                    "INSUFFICIENT_QUANTITY"
            );
        }

        product.setQuantity(product.getQuantity() - quantity);
        //productRepository.save(product);
        if(product.getQuantity()==0) {
        	product.setProductStatus("OUT OF STOCK");
        }
        else {
        	product.setProductStatus("HURRY UP TO PURCHASE");
        }
        productRepository.save(product);
        }else {
        	throw new ProductServiceCustomException(
                    "updating product name was not same",
                    "INVALID_PRODUCTNAME"
            );
        }
        log.info("Product Quantity updated Successfully");
        
    }

    @Override
    public ResponseEntity<String> deleteProductById(String productName, long productId) throws ProductServiceCustomException  {
        log.info("Product id: {}", productId);
        List<Product> product=productRepository.findByproductName(productName);
        Optional<Product> product1=   productRepository.findById(productId);
       
      if(product.isEmpty() || product1.get().getProductId()==productId)
        {
        log.info("Deleting Product with id: {}", productId);
     //Optional<Product> product1=   productRepository.findById(productId);
    
     productRepository.deleteById(product1.get().getProductId());
        
        return new ResponseEntity<>("deleted",HttpStatus.OK);
        
        }
        else {
        	try {
				
			    log.info("Im in this loop {}", !productRepository.existsById(productId));
			    throw new ProductServiceCustomException(
			            "Product with given with Id: " + productId + " not found:",
			            "PRODUCT_NOT_FOUND");
			
		} catch (ProductServiceCustomException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
        	return new ResponseEntity<>("PRODUCT_NOT_FOUND",HttpStatus.NOT_FOUND);
       
        }
        }
      
        }
    
	
	public List<Product> getAllProduct()
	{
		return productRepository.findAll();
	}
}

