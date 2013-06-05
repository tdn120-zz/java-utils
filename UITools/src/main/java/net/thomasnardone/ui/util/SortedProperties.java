package net.thomasnardone.ui.util;

import java.util.Comparator;
import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

/**
 * Extension of Properties with sorted keys for alphabetic output. Neither optimized for performance nor thread-safety.
 * Shamelessly ripped from StackOverflow
 */
public class SortedProperties extends Properties {
	private static final long	serialVersionUID	= 1L;

	/**
	 * Called by Properties.toString().
	 */
	@Override
	public Set<Map.Entry<Object, Object>> entrySet() {
		Set<Map.Entry<Object, Object>> entrySet = super.entrySet();
		if (entrySet == null) {
			return entrySet;
		}

		Set<Map.Entry<Object, Object>> sortedSet = new TreeSet<>(new EntryComparator());
		sortedSet.addAll(entrySet);
		return sortedSet;
	}

	/**
	 * Called throughout by Properties, including Properties.store(OutputStream out, String comments).
	 */
	@Override
	public synchronized Enumeration<Object> keys() {
		return new Vector<>(keySet()).elements();
	}

	/**
	 * Called by Properties.stringPropertyNames() and this.keys().
	 */
	@Override
	public Set<Object> keySet() {
		Set<Object> keySet = super.keySet();
		if (keySet == null) {
			return keySet;
		}
		return new TreeSet<>(keySet);
	}

	/**
	 * Comparator for sorting Map.Entry by key Assumes non-null entries.
	 */
	class EntryComparator implements Comparator<Map.Entry<Object, Object>> {
		@Override
		public int compare(final Map.Entry<Object, Object> entry1, final Map.Entry<Object, Object> entry2) {
			return entry1.getKey().toString().compareTo(entry2.getKey().toString());
		}
	}
}