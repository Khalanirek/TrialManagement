package com.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.study.Study;
import com.domain.study.StudyNotFoundException;
import com.domain.study.StudyRepository;

@Service
public class StudyService {

	private StudyRepository studyRepository;

	@Autowired
	public StudyService(StudyRepository studyRepository) {
		this.studyRepository = studyRepository;
	}

	public Study saveStudy(Study study) {
		return studyRepository.save(study);
	}

	public Study getStudy(Long studyId) throws StudyNotFoundException {
		Optional<Study> study = this.studyRepository.findById(studyId);
		return study.orElseThrow(() -> new StudyNotFoundException(studyId));
	}

	public Study updateStudy(Study study) throws StudyNotFoundException {
		Optional<Study> loadedStudy = studyRepository.findById(study.getStudyId());
		if (loadedStudy.isPresent()) {
			return studyRepository.save(study);
		} else {
			throw new StudyNotFoundException(study.getStudyId());
		}
	}

	public void deleteStudy(Long studyId) {
		studyRepository.deleteById(studyId);
	}
}
