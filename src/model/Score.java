package model;

import java.time.LocalDate;

public class Score {

	/**
	 * User of the score
	 */
	private User user;

	/**
	 * Score achieved
	 */
	private double score;

	/**
	 * Duration of the match
	 */
	private long duration;

	/**
	 * Date of the match
	 */
	private LocalDate date;

	/**
	 * Previous Score in the linked list
	 */
	private Score prev;

	/**
	 * Next Score in the linked list
	 */
	private Score next;

	/**
	 * 
	 * @param user     User of the score
	 * @param score    Score achieved
	 * @param duration Duration of the match
	 * @param date     Date of the match
	 */
	public Score(User user, double score, long duration, LocalDate date) {

		this.user = user;
		this.score = score;
		this.duration = duration;
		this.date = date;

		this.prev = null;
		this.next = null;

	}

	/**
	 * Returns the description of the Score
	 */
	@Override
	public String toString() {
		return user.toString() + " " + score;
	}

	/**
	 * Returns the previous Score in the linked list
	 * 
	 * @return Score score
	 */
	public Score getPrev() {
		return prev;
	}

	/**
	 * Sets the previous Score in the linked list
	 * 
	 * @param prev previous score
	 */
	public void setPrev(Score prev) {
		this.prev = prev;
	}

	/**
	 * Returns the next Score in the linked list
	 * 
	 * @return next score
	 */
	public Score getNext() {
		return next;
	}

	/**
	 * Sets the next Score in the linked list
	 * 
	 * @param next next score
	 */
	public void setNext(Score next) {
		this.next = next;
	}

	/**
	 * Returns the user of the score
	 * 
	 * @return User user of the score
	 */
	public User getUser() {
		return user;
	}

	/**
	 * Returns the username of the User
	 * 
	 * @return String username
	 */
	public String getUsername() {

		String username = "null";

		if (user != null) {
			username = user.getUsername();
		}

		return username;
	}

	/**
	 * Returns the score obtained
	 * 
	 * @return double score obtained
	 */
	public double getScore() {
		return score;
	}

	/**
	 * Returns the duration of the match in ms
	 * 
	 * @return long duration of the match in ms
	 */
	public long getDuration() {
		return duration;
	}

	/**
	 * Returns the duration of the match formatted in mm:ss
	 * 
	 * @return String formatted duration in mm:ss
	 */
	public String getFormattedDuration() {

		long seconds = (duration / 1000) % 60;
		long minutes = (duration / 60000) % 60;
		String sSec = seconds < 10 ? ("0" + seconds) : ("" + seconds);
		String sMin = minutes < 10 ? ("0" + minutes) : ("" + minutes);

		return sMin + ":" + sSec;
	}

	/**
	 * Returns the date of the score
	 * 
	 * @return LocalDate date of the score
	 */
	public LocalDate getDate() {
		return date;
	}

}
