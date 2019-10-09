package com.domain.study;


import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.domain.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name="study")
public class Study {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="study_id")
	private Long studyId;

	@NotBlank @Size(min=1, max=50)
	@Column(name="title")
	private String title;

	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name="start_date")
	private Date startDate;

	@NotNull
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	@Column(name="end_date")
	private Date endDate;

	@NotNull
	@OneToOne
	@JoinColumn(name="ownder_id")
	private User owner;

	@NotNull
	@JsonIgnore
	@ManyToMany
	@JoinTable(name="study_user",
	joinColumns=@JoinColumn(name="study_id"),
	inverseJoinColumns=@JoinColumn(name="user_id"))
	private List<User> users;

	public Study() {

	}

	public Study(Long studyId, String title, Date startDate, Date endDate, User owner, List<User> users) {
		this.studyId = studyId;
		this.title = title;
		this.startDate = startDate;
		this.endDate = endDate;
		this.owner = owner;
		this.users = users;
	}

	public Long getStudyId() {
		return studyId;
	}

	public void setStudyId(Long studyId) {
		this.studyId = studyId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public User getOwner() {
		return owner;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}


}
