package net.thomasnardone.utils.comparator.string;

import java.util.Comparator;

public class NumberAscending implements Comparator<String> {
	@Override
	public int compare(final String lhs, final String rhs) {
		if (lhs == rhs) {
			return 0;
		} else if (lhs == null) {
			return 1;
		} else if (rhs == null) {
			return -1;
		} else {
			Double left = null;
			try {
				left = Double.valueOf(lhs);
			} catch (NumberFormatException e) {
			}
			Double right = null;
			try {
				right = Double.valueOf(rhs);
			} catch (NumberFormatException e) {
			}
			if ((left == null) && (right == null)) {
				return lhs.compareTo(rhs);
			} else if (left == null) {
				return 1;
			} else if (right == null) {
				return -1;
			} else {
				return left.compareTo(right);
			}
		}
	}
}
