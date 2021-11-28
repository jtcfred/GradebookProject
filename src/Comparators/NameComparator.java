package Comparators;

import java.util.Comparator;

import GradebookMenu.Assignment;

public class NameComparator implements Comparator<Assignment>{

	@Override
	public int compare(Assignment o1, Assignment o2) {
		if(o1.getName().equals(o2.getName())) {
			return 0;
		} else if(o1.getName().toLowerCase().compareTo(o2.getName().toLowerCase()) > 0) {
			return 1;
		} else {
			return -1;
		}
	}

}
