package model;

import java.util.Comparator;

public class ScoreDateComparator implements Comparator<Score> {

	/**
	 * Orders the scores first by date in decreased order, then by score in
	 * Decreased order and finally by duration in increased order
	 * 
	 * @param s1 Score 1
	 * @param s2 Score 2
	 */
	@Override
	public int compare(Score s1, Score s2) {

		if (s1.getDate().compareTo(s2.getDate()) < 0) {
			return -1;
		} else if (s1.getDate().compareTo(s2.getDate()) > 0) {
			return 1;
		} else {

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
					return 0;
				}

			}

		}

	}

}
