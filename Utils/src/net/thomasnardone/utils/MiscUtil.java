package net.thomasnardone.utils;

public class MiscUtil {
	public static int intCompare(final int i1, final int i2) {
		return i1 > i2 ? 1 : i1 < i2 ? -1 : 0;
	}

	public static long toMillis(final long nanos) {
		return nanos / 1000000l;
	}
}
