package com.evoke.accessmanagement;

import java.util.Objects;

public class User {

	private Integer id;
	
	//assuming userName is always unique across users
	private String userName;

	
	public User(String userName) {
		this.userName = userName;
	}
	
	public User(Integer Id,String userName) {
		this.id = id;
		this.userName = userName;
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	@Override
	public int hashCode() {
		return Objects.hash(userName);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return Objects.equals(userName, other.userName);
	}


	
	
	

	
}
