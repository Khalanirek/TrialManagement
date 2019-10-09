package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
	public Study getStudy(@PathVariable Long studyId) throws StudyNotFoundException {
			return studyService.getStudy(studyId);
	}

	@PutMapping
	public Study updateStudy(@RequestBody Study study) throws StudyNotFoundException {
			return studyService.updateStudy(study);
	}

	@DeleteMapping("/{studyId}")
	public void deleteStudy(@PathVariable Long studyId) {
		studyService.deleteStudy(studyId);
	}
}
