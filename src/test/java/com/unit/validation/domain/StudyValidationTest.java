package com.unit.validation.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.domain.study.Study;
import com.domain.user.User;

@ExtendWith(MockitoExtension.class)
public class StudyValidationTest {

	private static final String STRING_SOURCE = "ABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJ";
	private static Validator validator;
	private Study study;
	private Set<ConstraintViolation<Study>> constraintViolations;

	@BeforeAll
	static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

	@Test
	void studyIsCorrect() {
		study = new Study(1L, STRING_SOURCE.substring(0, 50), new Date(), new Date(), new User(), new ArrayList<User>());
		constraintViolations = validator.validate(study);
		assertEquals(0, constraintViolations.size());
	}

	@Test
	void titleIsBlank() {
		study = new Study(1L, null, new Date(), new Date(), new User(), new ArrayList<User>());
		constraintViolations = validator.validate(study);
		assertEquals(1, constraintViolations.size());
		assertEquals("must not be blank", constraintViolations.iterator().next().getMessage());
	}

	@Test
	void titleIsTooLong() {
		study = new Study(1L, STRING_SOURCE.substring(0, 51), new Date(), new Date(), new User(), new ArrayList<User>());
		constraintViolations = validator.validate(study);
		assertEquals(1, constraintViolations.size());
		assertEquals("size must be between 1 and 50", constraintViolations.iterator().next().getMessage());
	}

	@Test
	void startDateIsNull() {
		study = new Study(1L, "TEST", null, new Date(), new User(), new ArrayList<User>());
		constraintViolations = validator.validate(study);
		assertEquals(1, constraintViolations.size());
		assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
	}

	@Test
	void endDateIsNull() {
		study = new Study(1L, "TEST", new Date(), null, new User(), new ArrayList<User>());
		constraintViolations = validator.validate(study);
		assertEquals(1, constraintViolations.size());
		assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
	}

	@Test
	void ownerIsNull() {
		study = new Study(1L, "TEST", new Date(), new Date(), null, new ArrayList<User>());
		constraintViolations = validator.validate(study);
		assertEquals(1, constraintViolations.size());
		assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
	}
	@Test
	void usersListIsNull() {
		study = new Study(1L, "TEST", new Date(), new Date(), new User(), null);
		constraintViolations = validator.validate(study);
		assertEquals(1, constraintViolations.size());
		assertEquals("must not be null", constraintViolations.iterator().next().getMessage());
	}
}
