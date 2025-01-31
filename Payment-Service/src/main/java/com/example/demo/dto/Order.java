package com.example.demo.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Order {
	
	private int id;
	private String name;
	private String category;
	private double price;
	private Date purchaseDate;
	private String orderId;
	private int userId;
	private String paymentMode;

}
