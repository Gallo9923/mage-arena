package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Log implements Serializable {

	private static final long serialVersionUID = 3143819941258172884L;

	/**
	 * Parent node of the BT
	 */
	private Log parent;

	/**
	 * Left node of the BT
	 */
	private Log left;

	/**
	 * Right node of the BT
	 */
	private Log right;

	/**
	 * date of the log
	 */
	private LocalDate date;

	/**
	 * sessionTime of the Log
	 */
	private long sessionTime;

	/**
	 * User of the log
	 */
	private User user;

	/**
	 * 
	 * @param user        User of the log
	 * @param sessionTime sessionTime of the Log
	 * @param date        date of the log
	 */
	public Log(User user, long sessionTime, LocalDate date) {
		this.user = user;
		this.sessionTime = sessionTime;
		this.date = date;

		this.parent = null;
		this.left = null;
		this.right = null;
	}

	/**
	 * Returns the representation of the object
	 * 
	 * @return String Representation of the object
	 */
	@Override
	public String toString() {
		return user.toString() + ", " + getFormattedSessionTime() + ", " + date;
	}

	/**
	 * Returns the parent node of the BT
	 * 
	 * @return Log Parent node of BT
	 */
	public Log getParent() {
		return parent;
	}

	/**
	 * Sets the parent node of the BT
	 * 
	 * @param parent Parent node of BT
	 */
	public void setParent(Log parent) {
		this.parent = parent;
	}

	/**
	 * Returns the left node of the BT
	 * 
	 * @return Log Left node of BT
	 */
	public Log getLeft() {
		return left;
	}

	/**
	 * Sets the left node of the BT
	 * 
	 * @param left Left node of the BT
	 */
	public void setLeft(Log left) {
		this.left = left;
	}

	/**
	 * Returns the right node of the BT
	 * 
	 * @return Right node of the BT
	 */
	public Log getRight() {
		return right;
	}

	/**
	 * Sets the right node of the BT
	 * 
	 * @param right Right node of the BT
	 */
	public void setRight(Log right) {
		this.right = right;
	}

	/**
	 * Returns the date of the log
	 * 
	 * @return LocalDate date of log
	 */
	public LocalDate getDate() {
		return date;
	}

	/**
	 * Returns the session time of the log
	 * 
	 * @return long session Time
	 */
	public long getSessionTime() {
		return sessionTime;
	}

	/**
	 * Returns the user of the log
	 * 
	 * @return User user
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Returns the username of the User
	 * @return String username
	 */
	public String getUsername() {
		return user.getUsername();
	}
	
	/**
	 * Returns the formatted session time mm:ss
	 * @return String formatted session time
	 */
	public String getFormattedSessionTime() {

		long seconds = (sessionTime / 1000) % 60;
		long minutes = (sessionTime / 60000) % 60;
		String sSec = seconds < 10 ? ("0" + seconds) : ("" + seconds);
		String sMin = minutes < 10 ? ("0" + minutes) : ("" + minutes);

		return sMin + ":" + sSec;

	}
}
