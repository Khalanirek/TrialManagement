package com.domain.study;

public class StudyNotFoundException extends Exception{
	public StudyNotFoundException(Long studyId) {
		super(String.format("Study with id %d not found", studyId));
	}
}
