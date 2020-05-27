package model;

import java.io.Serializable;

public class User implements Serializable {

	private static final long serialVersionUID = 977702602419014232L;

	/**
	 * Username of the user
	 */
	private String username;

	/**
	 * Password of the user
	 */
	private String password;

	/**
	 * The Parent User in the binary Tree
	 */
	private User parent;

	/**
	 * The Right User in the binary Tree
	 */
	private User right;

	/**
	 * The left User in the binary Tree
	 */
	private User left;

	/**
	 * Creates an instance of User
	 * 
	 * @param username Username of the User
	 * @param password Password of the User
	 */
	public User(String username, String password) {
		this.username = username;
		this.password = password;

		this.parent = null;
		this.right = null;
		this.left = null;
	}

	/**
	 * Returns the description of the user
	 */
	@Override
	public String toString() {
		return username + ", " + password;
	}

	/**
	 * Sets the parent user in the binary tree
	 * 
	 * @param parent parent user
	 */
	public void setParent(User parent) {
		this.parent = parent;
	}

	/**
	 * Sets the right user in the binary tree
	 * 
	 * @param right right user
	 */
	public void setRight(User right) {
		this.right = right;
	}

	/**
	 * Sets the left user in the binary tree
	 * 
	 * @param left left user
	 */
	public void setLeft(User left) {
		this.left = left;
	}

	/**
	 * Returns the username of the user
	 * 
	 * @return String username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * Returns the password of the user
	 * 
	 * @return String user password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Returns the parent user in the binary tree
	 * 
	 * @return User parent user
	 */
	public User getParent() {
		return parent;
	}

	/**
	 * Returns the right user in the binary tree
	 * 
	 * @return right user
	 */
	public User getRight() {
		return right;
	}

	/**
	 * Returns the left user in the binary tree
	 * 
	 * @return left user
	 */
	public User getLeft() {
		return left;
	}

}
