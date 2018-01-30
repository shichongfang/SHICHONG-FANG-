package com.example.D16124907.entity;

/**
 * user entity
 */
public class UserInfo {
	private String number;
	private String password;
	private String userName;
	private String type;
	private String email;
	private int age;
	private String language;

	public UserInfo() {
	}

	public UserInfo(String number) {
		this.number = number;
	}

	public UserInfo(String number, String password, String userName, String type, String email, int age, String sex) {
		this.number = number;
		this.password = password;
		this.userName = userName;
		this.type = type;
		this.email = email;
		this.age = age;
		this.language = language;
	}

	public String getNumber() {
		return number;
	}

	public void setNumber(String number) {
		this.number = number;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

}
