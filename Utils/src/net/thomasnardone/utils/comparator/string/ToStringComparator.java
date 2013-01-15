package net.thomasnardone.utils.comparator.string;

import java.util.Comparator;

public class ToStringComparator implements Comparator<Object> {
	private final Comparator<String>	stringComparator;

	public ToStringComparator(final Comparator<String> stringComparator) {
		this.stringComparator = stringComparator;
	}

	public int compare(final Object lhs, final Object rhs) {
		if (lhs == rhs) {
			return 0;
		} else if (lhs == null) {
			return -1;
		} else if (rhs == null) {
			return 1;
		} else {
			return stringComparator.compare(lhs.toString(), rhs.toString());
		}
	}

}
