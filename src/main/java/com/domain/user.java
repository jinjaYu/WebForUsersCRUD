package com.domain;

public class user {
	private String name;
	private String pwd;
	private int id;
	private int grade;
	private String email;
	
	public user() {
		
	}
	public user(int id, String name, String email, int grade, String pwd) {
		this.name = name;
		this.pwd = pwd;
		this.id = id;
		this.grade = grade;
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getGrade() {
		return grade;
	}
	public void setGrade(int grade) {
		this.grade = grade;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
