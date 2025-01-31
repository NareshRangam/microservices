package com.example.demo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.dto.OrderResponseDto;
import com.example.demo.entity.Order;
import com.example.demo.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	
	@Autowired
	private OrderService orderService;
	
	 private static Logger log = LoggerFactory.getLogger(OrderController.class);
	@PostMapping
	public String placeAnOrder(@RequestBody Order order)
	{
		return orderService.placeAnOrder(order);
	}
	
	@GetMapping("/{orderId}")
	public OrderResponseDto getOrderDetails(@PathVariable String orderId)
	{
		log.info("fetching the order details of {}",orderId);
		return orderService.getOrderDetails(orderId);
	}

}
