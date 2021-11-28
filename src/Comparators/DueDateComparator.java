package Comparators;

import java.util.Comparator;

import GradebookMenu.Assignment;

public class DueDateComparator implements Comparator<Assignment>{

	@Override
	public int compare(Assignment o1, Assignment o2) {
		if(o1.getDate().equals(o2.getDate())) {
			return 0;
		} else if(o1.getDate().compareTo(o2.getDate()) > 0) {
			return 1;
		} else {
			return -1;
		}
	}

}
