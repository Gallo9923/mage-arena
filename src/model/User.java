package model;

import java.io.Serializable;

public class User implements Serializable{

	private static final long serialVersionUID = 977702602419014232L;
	private String username;
	private String password;

	private User parent;
	private User right;
	private User left;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
		
		this.parent = null;
		this.right = null;
		this.left = null;
	}

	@Override
	public String toString() {
		return username + ", " + password; 
	}
	
	public void setParent(User parent) {
		this.parent = parent;
	}

	public void setRight(User right) {
		this.right = right;
	}

	public void setLeft(User left) {
		this.left = left;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public User getParent() {
		return parent;
	}

	public User getRight() {
		return right;
	}

	public User getLeft() {
		return left;
	}

}
