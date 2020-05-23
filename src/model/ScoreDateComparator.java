package model;

import java.util.Comparator;

public class ScoreDateComparator implements Comparator<Score>{

	// 1:Date decreased order, 2:Score decreased order, 3:Duration Increased order
	@Override
	public int compare(Score s1, Score s2) {
		
		if(s1.getDate().compareTo(s2.getDate()) < 0) {
			return -1;
		}else if(s1.getDate().compareTo(s2.getDate()) > 0) {
			return 1;
		}else {
			
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
