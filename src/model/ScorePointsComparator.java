package model;

import java.util.Comparator;

public class ScorePointsComparator implements Comparator<Score> {

	@Override
	public int compare(Score s1, Score s2) {

		// Ordered 1-Score: decreased order, 2-Duration: Increased order, 3-Date: decreased order
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
				}else {
					return 0;
				}

			}

		}

	}

}
