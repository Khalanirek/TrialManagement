package com.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import com.domain.study.Study;
import com.domain.user.User;

import base.MvcIntegrationTest;
import base.MvcJsonResult;

@ExtendWith(MockitoExtension.class)
public class StudyIntegrationTest extends MvcIntegrationTest{

	private static final String STUDY_URI = "/studies";
	private Study study;

	@Override
	@BeforeEach
    public void setUp() {
		super.setUp();
		long studyId = 0;
		String title = "Title";
		Date startDate = new Date();
		Date endDate = new Date();
		User owner = new User(1L, "TEST", "TEST", "TEST");
		List<User> users = new ArrayList<>();
		study = new Study(studyId, title, startDate, endDate, owner, users);
    }

	@Test
	void shouldSaveStudy() {
	    MvcJsonResult<User> postResult = post(STUDY_URI).withContent(study).forJsonOf(Study.class);
        assertEquals(HttpStatus.OK.value(), postResult.getMvcResult().getResponse().getStatus());
	}

	@Test
	void shouldFindStudy() {
		MvcJsonResult<Study> postResult = post(STUDY_URI).withContent(study).forJsonOf(Study.class);
    	Long studyId = postResult.getJson().getStudyId();
    	MvcJsonResult<Study> getResult = get(STUDY_URI + "/" + studyId).forJsonOf(Study.class);
    	assertEquals(HttpStatus.OK.value(), getResult.getMvcResult().getResponse().getStatus());
    	assertEquals(studyId, getResult.getJson().getStudyId());
	}

	@Test
	void shouldNotFindStudy() {
		MvcJsonResult<Study> getResult = get(STUDY_URI + "/" + -1L).forJsonOf(Study.class);
    	assertEquals(HttpStatus.NOT_FOUND.value(), getResult.getMvcResult().getResponse().getStatus());
	}

	@Test
	void shouldUpdateStudy() {
		MvcJsonResult<Study> postResult = post(STUDY_URI).withContent(study).forJsonOf(Study.class);
    	Study savedStudy = postResult.getJson();
    	savedStudy.setTitle("Update");
    	MvcJsonResult<Study> putResult = put(STUDY_URI).withContent(savedStudy).forJsonOf(Study.class);
    	assertEquals(HttpStatus.OK.value(), putResult.getMvcResult().getResponse().getStatus());
    	assertEquals(savedStudy.getStudyId(), putResult.getJson().getStudyId());
	}

	@Test
	void shouldNotUpdateStudy() {
		study.setStudyId(-1L);
		MvcJsonResult<Study> putResult = put(STUDY_URI).withContent(study).forJsonOf(Study.class);
    	assertEquals(HttpStatus.NOT_FOUND.value(), putResult.getMvcResult().getResponse().getStatus());
	}

	@Test
	void shouldDeleteStudy() {
		MvcJsonResult<Study> postResult = post(STUDY_URI).withContent(study).forJsonOf(Study.class);
    	Study savedStudy = postResult.getJson();
    	MvcJsonResult<Study> deleteResult = delete(STUDY_URI + "/" + savedStudy.getStudyId()).forJsonOf(Study.class);
    	assertEquals(HttpStatus.OK.value(), deleteResult.getMvcResult().getResponse().getStatus());
	}
}
