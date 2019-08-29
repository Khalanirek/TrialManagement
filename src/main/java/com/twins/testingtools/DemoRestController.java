package com.twins.testingtools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class DemoRestController {
	private final Logger LOG = LogManager.getLogger();
	// add code for the "/hello" endpoint

	@GetMapping("/hello")
	public String sayHello() {
		return "Hello World!";
	}

	@GetMapping("/greeting")
	public Greeting greeting() {
		LOG.debug("Debug message");
		LOG.info("Information message");
		LOG.warn("Warning message");
		LOG.error("Error message");
		LOG.fatal("Fatal message");
		return new Greeting(0, "TEST GREETING");
	}

}