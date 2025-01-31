package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class User {
	
	
	private int id;
	private String name;
	private String email;
	private String paymentMethod;
	private String srcAccount;
	private double availableAmount;
	

}
