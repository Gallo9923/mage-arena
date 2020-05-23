package model;

import java.time.LocalDate;

public class Score {

	private User user;
	private double score;
	private long duration;
	private LocalDate date;
	
	private Score prev;
	private Score next;

	
	public Score(User user, double score, long duration, LocalDate date) {
		
		this.user = user;
		this.score = score;
		this.duration = duration;
		this.date = date;
		
		this.prev = null;
		this.next = null;
		
	}
	
	@Override
	public String toString() {
		return user.toString() + " " +  score;
	}

	public Score getPrev() {
		return prev;
	}

	public void setPrev(Score prev) {
		this.prev = prev;
	}

	public Score getNext() {
		return next;
	}

	public void setNext(Score next) {
		this.next = next;
	}

	public User getUser() {
		return user;
	}

	public String getUsername() {
		
		String username = "null";
		
		if(user != null) {
			username = user.getUsername();
		}
	
		return username;
	}
	
	public double getScore() {
		return score;
	}

	public long getDuration() {
		return duration;
	}

	public String getFormattedDuration() {

		long seconds = (duration / 1000) % 60;
		long minutes = (duration / 60000) % 60;
		String sSec = seconds < 10 ? ("0" + seconds) : ("" + seconds);
		String sMin = minutes < 10 ? ("0" + minutes) : ("" + minutes);

		return sMin + ":" + sSec;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	
	

}
