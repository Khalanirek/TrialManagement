package com.trialman.testingtools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TestService {

	@Autowired private TestRepository testRepository;

	public Greeting saveGreeting(Greeting greeting)  {
		return testRepository.save(greeting);
	}

	public Greeting getGreeting(Long id)  {
		return testRepository.findById(id).get();
	}
}
