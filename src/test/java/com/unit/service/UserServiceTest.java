package com.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.domain.user.User;
import com.domain.user.UserNotFoundException;
import com.domain.user.UserRepository;
import com.service.UserService;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	private User user;
	private ArrayList<User> users;

	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	@BeforeEach
    void setUp() {
		long id = 1;
		String name = "User";
		String surname = "Surname";
		String jobPosition = "Tester";
        user = new User(id, name, surname, jobPosition);
        users = new ArrayList<>();
        users.add(user);
    }

	@Test
	void whenSaveUserThenSaveUser() {
		when(userRepository.save(user)).thenReturn(user);
		assertEquals(user, userService.saveUser(user));
		verify(userRepository).save(user);
	}

	@Test
	void whenGetUserThenGetUser() throws UserNotFoundException {
		when(userRepository.findById(any())).thenReturn(Optional.of(user));
		assertEquals(user, userService.getUser(user.getUserId()));
		verify(userRepository).findById(any());
	}

	@Test
	void whenGetUserWithWrongIdThenThrowUserNotFoundException() {
		when(userRepository.findById(-1L)).thenReturn(Optional.empty());
		user.setUserId(-1L);
		assertThrows(UserNotFoundException.class, () -> userService.getUser(user.getUserId()));
		verify(userRepository).findById(any());
	}

	@Test
	void whenUpdateUserThenUpdateUser() throws UserNotFoundException {
		when(userRepository.findById(any())).thenReturn(Optional.of(user));
		when(userRepository.save(user)).thenReturn(user);
		assertEquals(user, userService.updateUser(user));
		verify(userRepository).findById(user.getUserId());
		verify(userRepository).save(user);
	}

	@Test
	void whenUpadeUserWithWrongIdThenThrowUserNotFoundException() {
		when(userRepository.findById(-1L)).thenReturn(Optional.empty());
		user.setUserId(-1L);
		assertThrows(UserNotFoundException.class, () -> userService.updateUser(user));
		verify(userRepository).findById(any());
	}

	@Test
	void shouldDeleteUser() {
		userService.deleteUser(user.getUserId());
		verify(userRepository).deleteById(user.getUserId());
	}

	@Test
	void whenGetAllByJobPositionThenFindAllByJobPosition() {
		when(userRepository.findAllByJobPosition("TEST")).thenReturn(users);
		assertEquals(users, userService.getAllUsersByJobPosition("TEST"));
		verify(userRepository).findAllByJobPosition(any());
	}
}
