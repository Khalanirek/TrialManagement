package com.trialman.testingtools;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="greeting")
public class Greeting {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
    private Long id;

	@Column(name="content")
    private String content;

	public void setId(Long id) {
		this.id = id;
	}
	public Long getId() {
		return this.id;
	}
    public void setContent(String content) {
    	this.content = content;
    }
    public String getContent() {
        return content;
    }
}
