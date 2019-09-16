package com.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.user.User;
import com.domain.user.UserNotFoundException;
import com.domain.user.UserRepository;

@Service
public class UserService {

	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User saveUser(User user) {
		return this.userRepository.save(user);
	}

	public User getUser(Long userId) throws UserNotFoundException {
		Optional<User> user = this.userRepository.findById(userId);
	return user.orElseThrow(() -> new UserNotFoundException(userId));
	}

	public Iterable<User> getAllUsersByJobPosition(String jobPosition) {
		return userRepository.findAllByJobPosition(jobPosition);
	}

	public User updateUser(User user) throws UserNotFoundException {
		Optional<User> loadedUser = this.userRepository.findById(user.getUserId());
		if (loadedUser.isPresent()) {
			return userRepository.save(user);
		} else {
			throw new UserNotFoundException(user.getUserId());
		}
	}

	public void deleteUser(Long userId) {
		userRepository.deleteById(userId);
	}
}
