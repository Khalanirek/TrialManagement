package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.domain.study.Study;
import com.domain.study.StudyNotFoundException;
import com.service.StudyService;

@RestController
@RequestMapping("/studies")
public class StudyController {

	private StudyService studyService;

	@Autowired
	public StudyController(StudyService studyService) {
		this.studyService = studyService;
	}
	@PostMapping
	public Study addStudy(@RequestBody Study study) {
		return studyService.saveStudy(study);
	}

	@GetMapping("/{studyId}")
	public Study getStudy(@PathVariable Long studyId) {
		try {
			return studyService.getStudy(studyId);
		} catch (StudyNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

	@PutMapping
	public Study updateStudy(@RequestBody Study study) {
		try {
			return studyService.updateStudy(study);
		} catch (StudyNotFoundException e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e);
		}
	}

	@DeleteMapping("/{studyId}")
	public void deleteStudy(@PathVariable Long studyId) {
		studyService.deleteStudy(studyId);
	}
}
