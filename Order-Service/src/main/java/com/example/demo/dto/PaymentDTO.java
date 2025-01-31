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
public class PaymentDTO {
	private String payMode;
	private double amount;
	private Date paidDate;
	private String paymentStatus;

}
