package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping
	public User registerNewUser(@RequestBody User user)
	{
		return userService.addUser(user);
	}

	@GetMapping("/{userId}")
	public User getUser(@PathVariable int userId)
	{
		return userService.getUser(userId);
	}
	
	@PutMapping("/{userId}/{orderAmount}")
	public User updateBalance(@PathVariable int userId,@PathVariable double orderAmount)
	{
		return userService.updateAccountStatus(userId, orderAmount);
	}
}
