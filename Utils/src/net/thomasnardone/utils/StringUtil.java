package net.thomasnardone.utils;

public class StringUtil {
	public static String capitalize(final String s) {
		return s.replaceFirst(String.valueOf(s.charAt(0)), String.valueOf(Character.toUpperCase(s.charAt(0))));
	}

	public static String deCapitalize(final String s) {
		return s.replaceFirst(String.valueOf(s.charAt(0)), String.valueOf(Character.toLowerCase(s.charAt(0))));
	}

	public static String splitCamelCase(String s) {
		s = s.replaceAll(
				String.format("%s|%s|%s", "(?<=[A-Z])(?=[A-Z][a-z])", "(?<=[^A-Z])(?=[A-Z])", "(?<=[A-Za-z])(?=[^A-Za-z])"), " ");
		return capitalize(s);
	}
}
