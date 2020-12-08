package com.app.comparator;

import java.util.Comparator;

/**
 * 
 * @author
 *
 */
public class AComparator implements Comparator<ASortModel> {

	@Override
	public int compare(ASortModel o1, ASortModel o2) {
		if (o1.sortLetters.equals("@") || o2.sortLetters.equals("#")) {
			return -1;
		} else if (o1.sortLetters.equals("#") || o2.sortLetters.equals("@")) {
			return 1;
		} else {
			return o1.sortLetters.compareTo(o2.sortLetters);
		}
	}

}
