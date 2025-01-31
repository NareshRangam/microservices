package com.example.demo.consumer;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.demo.dto.Order;
import com.example.demo.dto.User;
import com.example.demo.entity.Payment;
import com.example.demo.repository.PaymentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class OrderProcessingConsumer {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private PaymentRepository paymentRepository;
	
	@KafkaListener(topics = "ORDER-PAYMENT-TOPIC")
	public void processOrder(String orderJsonString)
	{
		try {
			Order order = new ObjectMapper().readValue(orderJsonString, Order.class);
			
			// Build payment request
			Payment payment = Payment.builder()
					.amount(order.getPrice())
					.orderId(order.getOrderId())
					.paidDate(new Date())
					.userId(order.getUserId())
					.payMode(order.getPaymentMode())
					.build();
			if(payment.getPayMode().equalsIgnoreCase("COD"))
			{
				payment.setPaymentStatus("PENDING");
			}
			else
			{
				User user = restTemplate.getForObject("http://USER-SERVICE/users/"+payment.getUserId(), User.class);
				if(user.getAvailableAmount() < payment.getAmount())
				{
					throw new RuntimeException("Insufficient funds");
				}
				else
				{
					payment.setPaymentStatus("PAID");
					//paymentRepository.save(payment);
					
					// update user amount 
					restTemplate.put("http://USER-SERVICE/users/"+payment.getUserId()+"/"+payment.getAmount(), null);
					
				}
			}
			
			paymentRepository.save(payment);
			
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
