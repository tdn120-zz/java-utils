package net.thomasnardone.utils;

import java.util.List;

public class ListUtil {
	public static <T> boolean listsContainSameItems(final List<T> list1, final List<T> list2) {
		if (list2.size() != list1.size()) {
			return false;
		} else {
			for (T item : list2) {
				if (!list1.contains(item)) {
					return false;
				}
			}
		}
		return true;
	}
}
