package net.thomasnardone.utils;

/**
 * Utility methods relating to issues with null values
 * 
 * @author Thomas Nardone
 */
public class NullUtil {
	public static boolean equals(final Object o1, final Object o2) {
		return (o1 == o2) || ((o1 != null) && o1.equals(o2));
	}

	/**
	 * @return <tt>value</tt>, unless it is <tt>null</tt>. In that case it returns <tt>defaultValue</tt>.
	 */
	public static <T> T isNull(final T value, final T defaultValue) {
		return value == null ? defaultValue : value;
	}

	public static String toStringOrBlank(final Object o) {
		return o == null ? "" : String.valueOf(o);
	}

	/**
	 * @return <tt>s</tt> trimmed, or <tt>null</tt> if <tt>s</tt> is <tt>null</tt>.
	 */
	public static String trim(final String s) {
		return s == null ? null : s.trim();
	}

	/**
	 * @return <tt>s</tt> trimmed, or <tt>""</tt> if <tt>s</tt> is <tt>null</tt>.
	 */
	public static String trimOrBlank(final String s) {
		return s == null ? "" : s.trim();
	}
}
