package com.iiht.productapp.model;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;


import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Product {
	
	    @Id
	    @GeneratedValue(strategy = GenerationType.AUTO)
	    @Column(name="PRODUCT_ID")
	    private long productId;

	    @Column(name = "PRODUCT_NAME")
	    private String productName;

	     
	    @Column(name = "PRICE")
	    private long price;

	    @Column(name = "QUANTITY")
	    private long quantity;
	    
	    @Column(name = "Product_Description")
	   private String productDescription;
	    
	    @Column(name = "Features")
	   private String features;
	    
	 
	   
	   @Column(name="Product_Status")
	   private String productStatus;
}
