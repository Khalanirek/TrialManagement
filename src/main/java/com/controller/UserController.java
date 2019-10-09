package com.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.domain.user.User;
import com.domain.user.UserNotFoundException;
import com.microservice.MessageMicroservice;
import com.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {


	private UserService userService;
	private MessageMicroservice messageMicroservice;

	@Autowired
	public UserController(UserService userService, MessageMicroservice messageMicroservice) {
		this.userService = userService;
		this.messageMicroservice = messageMicroservice;
	}

	@PostMapping
	public User addUser(@Valid @RequestBody User user) {
		return userService.saveUser(user);
	}

	@GetMapping("/{userId}")
	public User getUser(@PathVariable Long userId) throws UserNotFoundException {
			//messageMicroservice.sendMessage();
			return userService.getUser(userId);
	}

	@GetMapping("/all/{jobPosition}")
	public Iterable<User> getAllUsersByJobPosition(@PathVariable String jobPosition){
		return userService.getAllUsersByJobPosition(jobPosition);
	}

	@PutMapping
	public User updateUser(@Valid @RequestBody User user) throws UserNotFoundException{
			return userService.updateUser(user);
	}

	@DeleteMapping("/{userId}")
	public void deleteUser(@PathVariable Long userId) {
		userService.deleteUser(userId);
	}
}
