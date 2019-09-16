package com.integration;


import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.domain.user.User;

import base.MvcIntegrationTest;
import base.MvcJsonResult;

@ExtendWith(MockitoExtension.class)
public class UserIntegrationTest extends MvcIntegrationTest{

	private static final String USER_URI = "/users";
	private User user;

	@Override
	@BeforeEach
    public void setUp() {
		super.setUp();
		long id = 0;
		String name = "User";
		String surname = "Surname";
		String jobPosition = "Tester";
        user = new User(id, name, surname, jobPosition);
    }

    @Test
    void shouldSaveUser() {
        MvcJsonResult<User> postResult = post(USER_URI).withContent(user).forJsonOf(User.class);
        assertEquals(HttpStatus.OK.value(), postResult.getMvcResult().getResponse().getStatus());
    }

    @Test
    void shouldGetUserById() {
    	MvcJsonResult<User> postResult = post(USER_URI).withContent(user).forJsonOf(User.class);
    	Long userId = postResult.getJson().getUserId();
    	MvcJsonResult<User> getResult = get(USER_URI + "/" + userId).forJsonOf(User.class);
    	assertEquals(HttpStatus.OK.value(), getResult.getMvcResult().getResponse().getStatus());
    	assertEquals(userId, getResult.getJson().getUserId());
    }

    @Test
    void shouldNotFindUser() {
    	user.setUserId(-1L);
    	MvcJsonResult<User> getResult = get(USER_URI + "/" + user.getUserId()).forJsonOf(User.class);
    	assertEquals(HttpStatus.NOT_FOUND.value(), getResult.getMvcResult().getResponse().getStatus());
    }

    @Test
    void shouldGetAllUsersByJobPosition() {
    	user.setJobPosition("Expected");
    	MvcJsonResult<User> postResult = post(USER_URI).withContent(user).forJsonOf(User.class);
    	post(USER_URI).withContent(user).forJsonOf(User.class);
    	user.setJobPosition("NotExpected");
    	post(USER_URI).withContent(user).forJsonOf(User.class);
    	MvcJsonResult<ArrayList<User>> getResult = get(USER_URI + "/all/Expected").forJsonOf(ArrayList.class, User.class);
    	for(int i = 0; i < getResult.getJson().size(); i++) {
    		assertEquals("Expected", getResult.getJson().get(i).getJobPosition());
    	}
    }

    @Test
    void shouldUpdateUser() {
    	MvcJsonResult<User> postResult = post(USER_URI).withContent(user).forJsonOf(User.class);
    	User savedUser = postResult.getJson();
    	savedUser.setName("Update");
    	MvcJsonResult<User> putResult = put(USER_URI).withContent(savedUser).forJsonOf(User.class);
    	assertEquals(HttpStatus.OK.value(), putResult.getMvcResult().getResponse().getStatus());
    	assertEquals(savedUser.getUserId(), putResult.getJson().getUserId());
    }

    @Test
    void shouldNotUpdateUser() {
    	user.setUserId(-1L);
    	MvcJsonResult<User> putResult = put(USER_URI).withContent(user).forJsonOf(User.class);
    	assertEquals(HttpStatus.NOT_FOUND.value(), putResult.getMvcResult().getResponse().getStatus());
    }

    @Test
    void shouldDeleteUserById() {
    	MvcJsonResult<User> postResult = post(USER_URI).withContent(user).forJsonOf(User.class);
    	User savedUser = postResult.getJson();
    	MvcJsonResult<User> deleteResult = delete(USER_URI + "/" + savedUser.getUserId()).forJsonOf(User.class);
    	assertEquals(HttpStatus.OK.value(), deleteResult.getMvcResult().getResponse().getStatus());
    }
}
