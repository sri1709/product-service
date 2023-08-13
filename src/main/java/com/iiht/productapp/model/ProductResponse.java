package com.iiht.productapp.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;

import org.hibernate.annotations.Entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("deprecation")
@Data

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ProductResponse {


	private long productId;
	private String productName;
	private long quantity;
	private long price;
	private String productDescription;
	private String features;

	

}
