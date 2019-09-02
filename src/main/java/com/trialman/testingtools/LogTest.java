package com.trialman.testingtools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LogTest {

	private static final Logger log = LogManager.getLogger("TEST");
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		log.error("TEST");
	}

}
