package net.thomasnardone.utils;

public class ExceptionUtil {
	public static Throwable getRootCause(Throwable e) {
		while (e.getCause() != null) {
			e = e.getCause();
		}
		return e;
	}
}
