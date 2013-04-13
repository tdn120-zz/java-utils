package net.thomasnardone.utils;

import java.util.Collection;
import java.util.Comparator;

import net.thomasnardone.utils.comparator.string.AscendingIgnoreCase;
import net.thomasnardone.utils.comparator.string.NumberAscending;
import net.thomasnardone.utils.comparator.string.ToStringComparator;

public class StringUtil {
	public static final int	COMPARE_ASCENDING_IGNORE_CASE	= 1;
	public static final int	COMPARE_NUMBER_ASCENDING		= 2;

	public static String capitalize(final String s) {
		return s.replaceFirst(String.valueOf(s.charAt(0)), String.valueOf(Character.toUpperCase(s.charAt(0))));
	}

	public static Comparator<String> comparator() {
		return comparator(COMPARE_ASCENDING_IGNORE_CASE);
	}

	public static Comparator<String> comparator(final int type) {
		switch (type) {
			case COMPARE_ASCENDING_IGNORE_CASE:
				return new AscendingIgnoreCase();
			case COMPARE_NUMBER_ASCENDING:
				return new NumberAscending();
			default:
				return new AscendingIgnoreCase();
		}
	}

	public static String deCapitalize(final String s) {
		return s.replaceFirst(String.valueOf(s.charAt(0)), String.valueOf(Character.toLowerCase(s.charAt(0))));
	}

	public static String[] fromCsv(final String s) {
		if ((s == null) || s.trim().isEmpty()) {
			return new String[0];
		}
		return s.split(",");
	}

	public static String pluralize(final String s) {
		if (s.endsWith("s") || s.endsWith("sh") || s.endsWith("ch") || s.endsWith("o")) {
			return s + "es";
		} else {
			return s + "s";
		}
	}

	public static String splitCamelCase(String s) {
		s = s.replaceAll(
				String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])", "(?<=[A-Za-z])(?=[^A-Za-z])"), " ");
		return capitalize(s);
	}

	public static String toCsv(final Collection<String> strings) {
		if ((strings == null) || strings.isEmpty()) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		for (String s : strings) {
			sb.append(s).append(",");
		}
		return sb.substring(0, sb.length() - 1);
	}

	public static Comparator<Object> toStringComparator() {
		return toStringComparator(COMPARE_ASCENDING_IGNORE_CASE);
	}

	public static Comparator<Object> toStringComparator(final int type) {
		return new ToStringComparator(comparator(type));
	}

	public static String underscore(String s) {
		s = s.replaceAll(
				String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])", "(?<=[A-Za-z])(?=[^A-Za-z])"), "_");
		return s.toLowerCase();
	}
}
