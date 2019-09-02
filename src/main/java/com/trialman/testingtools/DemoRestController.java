package com.trialman.testingtools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class DemoRestController {
	private final Logger LOG = LogManager.getLogger();
	// add code for the "/hello" endpoint
	@Autowired private TestService testService;
	public DemoRestController() {

	}
	public DemoRestController(TestService testService) {
		this.testService = testService;
	}
	@GetMapping("/hello")
	public String sayHello() {
		return "Hello World!";
	}

	@GetMapping("/greeting/{id}")
	public Greeting getGreeting(@PathVariable("id") Long id) {
		return testService.getGreeting(id);
	}

	@PostMapping("/greeting")
	public Greeting saveGreeting(@RequestBody Greeting greeting) {
		return testService.saveGreeting(greeting);
	}

}