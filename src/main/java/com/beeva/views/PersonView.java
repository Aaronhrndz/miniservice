package com.beeva.views;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public class PersonView {
	
	private String id;
	private String name;
	private Integer dateOfBirth;
	
	public PersonView() {
		
	}
	
	@JsonProperty
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@JsonProperty
	@NotNull
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@JsonProperty
	@NotNull
	public Integer getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Integer dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	@JsonProperty
	public Long getAge() {
		long currentMs = System.currentTimeMillis() / 1000;
		long age = (currentMs - this.dateOfBirth) / 3600 / 24 / 365;
		return Long.valueOf(age);
	}
	
	

}
