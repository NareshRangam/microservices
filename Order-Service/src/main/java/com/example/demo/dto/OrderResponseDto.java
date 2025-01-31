package com.example.demo.dto;

import com.example.demo.entity.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class OrderResponseDto {
	private String message;
	private Order order;
	private PaymentDTO paymentDTO;
	private UserDto userDto;

}
