package Comparators;

import java.util.Comparator;

import GradebookMenu.Assignment;

public class ScoreComparator implements Comparator<Assignment>{

	@Override
	public int compare(Assignment o1, Assignment o2) {
		if(o1.getScore() == o2.getScore()) {
			return 0;
		} else if(o1.getScore() > o2.getScore()) {
			return 1;
		} else {
			return -1;
		}
	}
	
}
