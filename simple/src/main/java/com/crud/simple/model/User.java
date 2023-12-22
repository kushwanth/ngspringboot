package com.crud.simple.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="users")
public class User{

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	
	@Column(name="first_name", nullable=false)
	private String first_name;

	@Column(name="last_name" ,nullable=false)
	private String last_name;
	
	public User() {
		
	}
	
	public User(String first_name, String last_name) {
		this.first_name = first_name;
		this.last_name = last_name;
	}
	
	public long getId() {
		return id;
	}
	
	public String getFirst_name() {
		return this.first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getLast_name() {
		return this.last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}
}