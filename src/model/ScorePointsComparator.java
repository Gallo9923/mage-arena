package model;

import java.util.Comparator;

public class ScorePointsComparator implements Comparator<Score> {

	/**
	 * Orders the scores first by score in decreased order, then by duration in
	 * increased order and finally by date in decreased order
	 * 
	 * @param s1 Score 1
	 * @param s2 Score 2
	 */
	@Override
	public int compare(Score s1, Score s2) {

		// Ordered 1-Score: decreased order, 2-Duration: Increased order, 3-Date:
		// decreased order
		if (s1.getScore() - s2.getScore() < 0) {
			return 1;
		} else if (s1.getScore() - s2.getScore() > 0) {
			return -1;
		} else {

			if (s1.getDuration() - s2.getDuration() < 0) {
				return -1;
			} else if (s1.getDuration() - s2.getDuration() > 0) {
				return 1;
			} else {

				if (s1.getDate().compareTo(s2.getDate()) < 0) {
					return -1;
				} else if (s1.getDate().compareTo(s2.getDate()) > 0) {
					return 1;
				} else {
					return 0;
				}

			}

		}

	}

}
