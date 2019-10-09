package com.unit.validation.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import com.domain.user.User;

@ExtendWith(MockitoExtension.class)
public class UserValidationTest {

	private static final String STRING_SOURCE = "ABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJABCDEFGHIJ";
	private static Validator validator;
	private User user;
	private Set<ConstraintViolation<User>> constraintViolations;

	@BeforeAll
	static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

	@Test
	void userIsCorrect() {
		user = new User(1L, "TEST", "TEST", "TEST");
		constraintViolations = validator.validate(user);
		assertEquals(0, constraintViolations.size());
	}

	@Test
	void nameIsBlank() {
		user = new User(1L, null, "TEST", "TEST");
		constraintViolations = validator.validate(user);
		assertEquals(1, constraintViolations.size());
		assertEquals("must not be blank", constraintViolations.iterator().next().getMessage());
	}

	@Test
	void nameIsTooLong() {
		user = new User(1L, STRING_SOURCE.substring(0, 51), "TEST", "TEST");
		constraintViolations = validator.validate(user);
		assertEquals(1, constraintViolations.size());
		assertEquals("size must be between 1 and 50", constraintViolations.iterator().next().getMessage());
	}

	@Test
	void surnameIsBlank() {
		user = new User(1L, "TEST", null, "TEST");
		constraintViolations = validator.validate(user);
		assertEquals(1, constraintViolations.size());
		assertEquals("must not be blank", constraintViolations.iterator().next().getMessage());
	}

	@Test
	void surnameIsTooLong() {
		user = new User(1L, "TEST", STRING_SOURCE.substring(0, 51), "TEST");
		constraintViolations = validator.validate(user);
		assertEquals(1, constraintViolations.size());
		assertEquals("size must be between 1 and 50", constraintViolations.iterator().next().getMessage());
	}

	@Test
	void jobPositionIsBlank() {
		user = new User(1L, "TEST", "TEST", null);
		constraintViolations = validator.validate(user);
		assertEquals(1, constraintViolations.size());
		assertEquals("must not be blank", constraintViolations.iterator().next().getMessage());
	}

	@Test
	void jobPositionIsTooLong() {
		user = new User(1L, "TEST", "TEST", STRING_SOURCE.substring(0, 51));
		constraintViolations = validator.validate(user);
		assertEquals(1, constraintViolations.size());
		assertEquals("size must be between 1 and 50", constraintViolations.iterator().next().getMessage());
	}
}
