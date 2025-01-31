package com.example.demo.service;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Lazy;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.OrderResponseDto;
import com.example.demo.dto.PaymentDTO;
import com.example.demo.dto.UserDto;
import com.example.demo.entity.Order;
import com.example.demo.repository.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
@RefreshScope
public class OrderService {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private KafkaTemplate<String, Object> kafkaTemplate;
	
	@Autowired
	@Lazy
	private RestTemplate restTemplate;
	
	@Value("${order.topicName}")
	private String topicName;
	
	@Value("${rangam.name}")
	private String name;
	
	
	//place an order method
	
	public String placeAnOrder(Order order)
	{
		// set purchasedate and dynamic orderid value
		
		order.setOrderId(UUID.randomUUID().toString().split("-")[0]);
		order.setPurchaseDate(new Date());
		//saving into order-service DB
		orderRepository.save(order);
		
		//send order object to payment service using kafka
		try {
			kafkaTemplate.send(topicName,new ObjectMapper().writeValueAsString(order));
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	return "Your order with "+ order.getOrderId()+ "has been placed successfully";
		
	}
	
	@CircuitBreaker(name = "orderService",fallbackMethod = "orderServiceFallback")
	public OrderResponseDto getOrderDetails(String orderId) {
		// TODO Auto-generated method stub
		System.out.println("name:"+name);
		Order order = orderRepository.findByOrderId(orderId);
		PaymentDTO paymentDto = restTemplate.getForObject("http://PAYMENT-SERVICE/payments/"+orderId, PaymentDTO.class);
		UserDto userDto = restTemplate.getForObject("http://USER-SERVICE/users/"+order.getUserId(), UserDto.class);
		
		return OrderResponseDto.builder()
				.order(order)
				.paymentDTO(paymentDto)
				.userDto(userDto)
				.build();
	}
	
	public OrderResponseDto orderServiceFallback(Exception ex)
	{
		return new OrderResponseDto("Failed",null, null, null);
	}

}
