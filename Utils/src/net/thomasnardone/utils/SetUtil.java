package net.thomasnardone.utils;

import java.util.HashSet;
import java.util.Set;

public class SetUtil {
	public static final <T> Set<T> newSet(final T item) {
		HashSet<T> set = new HashSet<T>();
		set.add(item);
		return set;
	}
}
