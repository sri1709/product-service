package com.iiht.productapp.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iiht.productapp.model.Product;
import com.iiht.productapp.model.ProductResponse;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>{
	   List<Product> findByproductName(String productName);	
}
