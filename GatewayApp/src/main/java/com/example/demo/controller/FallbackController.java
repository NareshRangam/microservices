package com.example.demo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/falback")
public class FallbackController {
	
	@GetMapping("/order")
	public ResponseEntity<String> orderFallBack(){
		return  ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT)
				.body("Order Service having issue! Please contact to help desk");
	}

	@GetMapping("/payment")
	public ResponseEntity<String> paymentFallBack(){
		return  ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT)
				.body("Payment Service having issue! Please contact to help desk");
	}

	@GetMapping("/user")
	public ResponseEntity<String> userFallBack(){
		return  ResponseEntity.status(HttpStatus.GATEWAY_TIMEOUT)
				.body("User Service having issue! Please contact to help desk");
	}

}
