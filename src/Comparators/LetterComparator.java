package Comparators;

import java.util.Comparator;

import GradebookMenu.Assignment;

public class LetterComparator implements Comparator<Assignment>{

	@Override
	public int compare(Assignment o1, Assignment o2) {
		if(o1.getLetter() == o2.getLetter()) {
			return 0;
		} else if(o1.getLetter() < o2.getLetter()) {
			return 1;
		} else {
			return -1;
		}
	}

}
