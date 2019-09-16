package com.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.controller.UserController;
import com.domain.user.User;
import com.domain.user.UserNotFoundException;
import com.service.UserService;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

	User user;
	ArrayList<User> users;

	@Mock
	UserService userService;

	@InjectMocks
	private UserController userController;

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
	void shouldAddUser() {
		when(userService.saveUser(user)).thenReturn(user);
		assertEquals(user, userController.addUser(user));
		verify(userService).saveUser(user);
	}

	@Test
	void shouldGetUser() throws UserNotFoundException {
		when(userService.getUser(user.getUserId())).thenReturn(user);
		assertEquals(user, userController.getUser(user.getUserId()));
		verify(userService).getUser(user.getUserId());

	}

	@Test
	void whenGetAllUsersWithJobPositionThenGetAllUsersWithJobPostion() {
		when(userService.getAllUsersByJobPosition("TEST")).thenReturn(users);
		assertEquals(users, userController.getAllUsersByJobPosition("TEST"));
		verify(userService).getAllUsersByJobPosition("TEST");
	}

	@Test
	void shouldUpdateUser() throws UserNotFoundException {
		when(userService.updateUser(user)).thenReturn(user);
		assertEquals(user, userController.updateUser(user));
		verify(userService).updateUser(user);
	}

	@Test
	void shouldDeleteUser() {
		userController.deleteUser(user.getUserId());
		verify(userService).deleteUser(user.getUserId());
	}

}
