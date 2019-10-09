package com.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.domain.user.User;
import com.microservice.MessageMicroservice;

@ExtendWith(MockitoExtension.class)
public class MessageMicroserviceTest {

	@Mock
	RestTemplate restTemplate;

	@InjectMocks
	private MessageMicroservice messageMicroservice;

	private User user;

	private static final String MESSAGE_MICROSERVICE_URI = "http://localhost:8080/trialmanagement-message-service";

	@BeforeEach
    public void setUp() {
		long id = 0;
		String name = "User";
		String surname = "Surname";
		String jobPosition = "Tester";
        user = new User(id, name, surname, jobPosition);
    }

	@Test
	void shouldSendUserWelcomeMessage() throws IOException, URISyntaxException {
		ResponseEntity<Boolean> response = new ResponseEntity<Boolean>(true, HttpStatus.OK);
		URI uri = new URI(MESSAGE_MICROSERVICE_URI + "/mails/mail");
		when(restTemplate.postForEntity(uri, user, Boolean.class)).thenReturn(response);
		assertEquals(response.getBody(), messageMicroservice.sendUserWelcomeMessage(user));
	}
}
