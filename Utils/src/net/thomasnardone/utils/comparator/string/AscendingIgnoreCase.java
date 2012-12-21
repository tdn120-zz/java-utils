package net.thomasnardone.utils.comparator.string;

import java.util.Comparator;

public class AscendingIgnoreCase implements Comparator<String> {
	public int compare(final String lhs, final String rhs) {
		if (lhs == rhs) {
			return 0;
		} else if (lhs == null) {
			return -1;
		} else if (rhs == null) {
			return 1;
		} else {
			return lhs.toUpperCase().compareTo(rhs.toUpperCase());
		}
	}
}
