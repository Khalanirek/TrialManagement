package com.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.controller.StudyController;
import com.domain.study.Study;
import com.domain.study.StudyNotFoundException;
import com.domain.user.User;
import com.service.StudyService;
@ExtendWith(MockitoExtension.class)
class StudyControllerTest {

	private Study study;

	@Mock
	private StudyService studyService;

	@InjectMocks
	private StudyController studyController;

	@BeforeEach
    void setUp() {
		long studyId = 1;
		String title = "TEST";
		Date startDate = new Date();
		Date endDate = new Date();
		User owner = new User(1L, "TEST", "TEST", "TEST");
		List<User> users = new ArrayList<>();
		study = new Study(studyId, title, startDate, endDate, owner, users);
    }

	@Test
	void shouldAddUser() {
		when(studyService.saveStudy(study)).thenReturn(study);
		assertEquals(study, studyController.addStudy(study));
		verify(studyService).saveStudy(study);
	}

	@Test
	void shouldGetStudy() throws StudyNotFoundException {
		when(studyService.getStudy(study.getStudyId())).thenReturn(study);
		assertEquals(study, studyController.getStudy(study.getStudyId()));
		verify(studyService).getStudy(study.getStudyId());
	}

	@Test
	void shouldUpdateStudy() throws StudyNotFoundException {
		when(studyService.updateStudy(study)).thenReturn(study);
		assertEquals(study, studyController.updateStudy(study));
		verify(studyService).updateStudy(study);
	}

	@Test
	void shouldDeleteStudy() {
		studyController.deleteStudy(study.getStudyId());
		verify(studyService).deleteStudy(study.getStudyId());
	}
}
