package com.unit.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.domain.study.Study;
import com.domain.study.StudyNotFoundException;
import com.domain.study.StudyRepository;
import com.domain.user.User;
import com.service.StudyService;

@ExtendWith(MockitoExtension.class)
class StudyServiceTest {

	private Study study;

	@Mock
	private StudyRepository studyRepository;

	@InjectMocks
	private StudyService studyService;

	@BeforeEach
    void setUp() {
		Long studyId = 1L;
		String title = "Test";
		Date startDate = new Date();
		Date endDate = new Date();
		User owner = new User(1L, "TEST", "TEST", "TEST");
		List<User> users = new ArrayList<>();
        study = new Study(studyId, title, startDate, endDate, owner, users);
    }

	@Test
	void shouldSaveStudy() {
		when(studyRepository.save(study)).thenReturn(study);
		assertEquals(study, studyService.saveStudy(study));
	}

	@Test
	void shouldGetStudy() throws StudyNotFoundException {
		when(studyRepository.findById(study.getStudyId())).thenReturn(Optional.of(study));
		assertEquals(study, studyService.getStudy(study.getStudyId()));
		verify(studyRepository).findById(study.getStudyId());
	}

	@Test
	void whenGetNotExistingStudyShouldThrowStudyNotFoundException() {
		when(studyRepository.findById(-1L)).thenReturn(Optional.empty());
		assertThrows(StudyNotFoundException.class, () -> studyService.getStudy(-1L));
		verify(studyRepository).findById(-1L);
	}

	@Test
	void shouldUpdateStudy() throws StudyNotFoundException {
		when(studyRepository.findById(study.getStudyId())).thenReturn(Optional.of(study));
		when(studyRepository.save(study)).thenReturn(study);
		assertEquals(study, studyService.updateStudy(study));
		verify(studyRepository).findById(study.getStudyId());
		verify(studyRepository).save(study);
	}

	@Test
	void whenUpdateNotExistingStudyShouldThrowStudyNotFoundException() {
		study.setStudyId(-1L);
		when(studyRepository.findById(-1L)).thenReturn(Optional.empty());
		assertThrows(StudyNotFoundException.class, () -> studyService.updateStudy(study));
		verify(studyRepository).findById(study.getStudyId());
		verify(studyRepository, never()).save(any());
	}

	@Test
	void shouldDeleteStudy() {
		studyService.deleteStudy(study.getStudyId());
		verify(studyRepository).deleteById(study.getStudyId());
	}
}
