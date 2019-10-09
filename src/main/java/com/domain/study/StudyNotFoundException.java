package com.domain.study;

import com.domain.error.ObjectNotFoundException;

public class StudyNotFoundException extends ObjectNotFoundException{
	public StudyNotFoundException(Long studyId) {
		super(String.format("Study with id %d not found", studyId));
	}
}
