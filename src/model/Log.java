package model;

import java.io.Serializable;
import java.time.LocalDate;

public class Log implements Serializable{

	private static final long serialVersionUID = 3143819941258172884L;
	
	private Log parent;
	private Log left;
	private Log right;
	
	private LocalDate date;
	private long sessionTime;
	private User user;
	
	public Log(User user, long sessionTime, LocalDate date) {
		this.user = user;
		this.sessionTime = sessionTime;
		this.date = date;
		
		this.parent = null;
		this.left = null;
		this.right = null;
	}

	@Override
	public String toString() {
		return user.toString() + ", " + getFormattedSessionTime() + ", " + date;
	} 
	
	public Log getParent() {
		return parent;
	}

	public void setParent(Log parent) {
		this.parent = parent;
	}

	public Log getLeft() {
		return left;
	}

	public void setLeft(Log left) {
		this.left = left;
	}

	public Log getRight() {
		return right;
	}

	public void setRight(Log right) {
		this.right = right;
	}

	public LocalDate getDate() {
		return date;
	}

	public long getSessionTime() {
		return sessionTime;
	}
	
	public User getUser() {
		return user;
	}
	
	public String getUsername() {
		return user.getUsername();
	}
	
	public String getFormattedSessionTime() {
		
		long seconds = (sessionTime / 1000) % 60;
		long minutes = (sessionTime / 60000) % 60;
		String sSec = seconds < 10 ? ("0" + seconds) : ("" + seconds);
		String sMin = minutes < 10 ? ("0" + minutes) : ("" + minutes);

		return sMin + ":" + sSec;
	
	}
}
