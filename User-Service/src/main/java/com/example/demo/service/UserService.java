package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.User;
import com.example.demo.repo.UserRepository;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User addUser(User user)
	{
		return userRepository.save(user);
	}
	
	public User getUser(int userId)
	{
		return userRepository.findById(userId)
				.orElseThrow(() -> new RuntimeException("User not found with id:"+userId));
	}
	
	public User updateAccountStatus(int userId,double amountUsed)
	{
		User user = getUser(userId);
		user.setAvailableAmount(user.getAvailableAmount() - amountUsed);
		return userRepository.save(user);
	}
}
