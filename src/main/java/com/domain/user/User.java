package com.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name="application_user")
public class User {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long userId;

	@NotBlank @Size(min=1, max=50)
	@Column(name="name")
	private String name;

	@NotBlank @Size(min=1, max=50)
	@Column(name="surname")
	private String surname;

	@NotBlank @Size(min=1, max=50)
	@Column(name="job_position")
	private String jobPosition;

	public User() {

	}

	public User(Long userId, String name, String surname, String jobPosition) {
		this.userId = userId;
		this.name = name;
		this.surname = surname;
		this.jobPosition = jobPosition;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getJobPosition() {
		return jobPosition;
	}

	public void setJobPosition(String jobPosition) {
		this.jobPosition = jobPosition;
	}
}
