package com.microservice;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.domain.user.User;

@Service
public class MessageMicroservice {

	private static final String MESSAGE_MICROSERVICE_URI = "http://localhost:8080/trialmanagement-message-service";

	private RestTemplate restTemplate;

	public MessageMicroservice(){
		this.restTemplate = new RestTemplate();
	}

	public MessageMicroservice(RestTemplate restTemplate){
		this.restTemplate = restTemplate;
	}

	public boolean sendUserWelcomeMessage(User user) throws IOException, URISyntaxException {
		URI uri = new URI(MESSAGE_MICROSERVICE_URI + "/mails/mail");
		ResponseEntity<Boolean> result = restTemplate.postForEntity(uri, user, Boolean.class);
		return result.getBody();
	}
}
