package net.thomasnardone.utils;

import java.util.Comparator;

import net.thomasnardone.utils.comparator.string.AscendingIgnoreCase;

public class StringUtil {
	public static final int	COMPARE_ASCENDING_IGNORE_CASE	= 1;

	public static String capitalize(final String s) {
		return s.replaceFirst(String.valueOf(s.charAt(0)), String.valueOf(Character.toUpperCase(s.charAt(0))));
	}

	public static Comparator<String> comparator(final int type) {
		switch (type) {
			case 1:
				return new AscendingIgnoreCase();
			default:
				return new AscendingIgnoreCase();
		}
	}

	public static String deCapitalize(final String s) {
		return s.replaceFirst(String.valueOf(s.charAt(0)), String.valueOf(Character.toLowerCase(s.charAt(0))));
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
}
