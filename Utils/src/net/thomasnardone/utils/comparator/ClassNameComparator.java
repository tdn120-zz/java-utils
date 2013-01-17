package net.thomasnardone.utils.comparator;

import java.util.Comparator;

public class ClassNameComparator implements Comparator<Class<?>> {
	public int compare(final Class<?> lhs, final Class<?> rhs) {
		if (lhs == rhs) {
			return 0;
		} else if (lhs == null) {
			return -1;
		} else if (rhs == null) {
			return 1;
		} else {
			return lhs.getName().toUpperCase().compareTo(rhs.getName().toUpperCase());
		}
	}
}
