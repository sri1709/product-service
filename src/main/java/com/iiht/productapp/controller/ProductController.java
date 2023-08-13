package com.iiht.productapp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.iiht.productapp.config.AppConfigs;
import com.iiht.productapp.config.AuthFeign;
import com.iiht.productapp.exception.InvalidTokenException;
import com.iiht.productapp.exception.ProductServiceCustomException;
import com.iiht.productapp.model.Product;
import com.iiht.productapp.model.ProductRequest;
import com.iiht.productapp.model.ProductResponse;
import com.iiht.productapp.service.*;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@RestController
@CrossOrigin(origins="http://localhost:4200",allowedHeaders="*",exposedHeaders="Authorization")
@RequestMapping("/api/v1.0/shopping")
@Slf4j
public class ProductController {
	
    @Autowired
    ProductService productService;

	@Autowired
	private AuthFeign authFeign;
	
	
	@Autowired
    private KafkaTemplate<String, UUID> kafkaTemplate;

	private UUID id;
	
	@PostMapping("{name}/add")
    public String addProduct(@RequestHeader("Authorization") String token,@PathVariable("name") String name, @RequestBody ProductRequest productRequest) throws InvalidTokenException {

        log.info("ProductController | addProduct is called");

        log.info("ProductController | addProduct | productRequest : " + productRequest.toString());
        if(authFeign.getValidity(token).getBody().isValid()) {
         List<ProductRequest> prod=new ArrayList();
         prod.add(productRequest);
         productService.addProduct(prod);
        }else {
        throw new InvalidTokenException("Token Expired or Invalid , Login again ...");
        }
        
        return "Product" + productRequest.getProductName() + "added Successfully";
         
    }

    @GetMapping("/{name}")
    public ResponseEntity<List<ProductResponse>> getProductByName(@RequestHeader("Authorization") String token,@PathVariable("name") String name) throws ProductServiceCustomException, InvalidTokenException {

        log.info("ProductController | getProductByName is called");

        log.info("ProductController | getProductByName | productName : " + name);
        if(authFeign.getValidity(token).getBody().isValid()) {
        return productService.getProductByName(name);
        }
        throw new InvalidTokenException("Token Expired or Invalid , Login again ...");
    }

    @PutMapping("/{name}/update/{id}")
    public ResponseEntity<Void> reduceQuantity(@RequestHeader("Authorization") String token,@PathVariable("name") String productName,
            @PathVariable("id") long productId,@RequestParam long quantity
           
    ) throws ProductServiceCustomException, InvalidTokenException {

        log.info("ProductController | reduceQuantity is called");

        log.info("ProductController | reduceQuantity | productId : " + productId);
        log.info("ProductController | reduceQuantity | quantity : " + quantity);
        if(authFeign.getValidity(token).getBody().isValid()) {
        productService.reduceQuantity(productName,productId,quantity);
        
        }
        else {
        	throw new InvalidTokenException("Token Expired or Invalid , Login again ...");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{name}/delete/{id}")
    public ResponseEntity<String> deleteProductById(@RequestHeader("Authorization") String token,@PathVariable("name") String productName,@PathVariable("id") long productId) throws ProductServiceCustomException, InvalidTokenException {
    	if(authFeign.getValidity(token).getBody().isValid()) {
    		
    return 	productService.deleteProductById(productName,productId);
    	}
    	throw new InvalidTokenException("Token Expired or Invalid , Login again ...");
        
        
    }
    
    @GetMapping("/all")
	@ApiOperation(notes = "Returns the Product Details", value = "Find All Product Details")
	public List<Product> getAllProduct(@RequestHeader("Authorization") String token) throws ProductServiceCustomException, InvalidTokenException
	{
    	if(authFeign.getValidity(token).getBody().isValid()) {
			
    		return productService.getAllProduct();
		
    	}
    	throw new InvalidTokenException("Token Expired or Invalid , Login again ...");
	
		
		
	}
}
