package net.thomasnardone.utils;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class ReadyUtil {
	private static final List<String>	MONTHS				= Arrays.asList("JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL",
																	"AUG", "SEP", "OCT", "NOV", "DEC");
	private static final String			READY_SPLIT			= "[\\s_]";
	private static Comparator<String>	stringComparator	= StringUtil.comparator();

	public static int compare(final String left, final String right) {
		if (left == right) {
			return 0;
		} else if (left == null) {
			return -1;
		} else if (right == null) {
			return 1;
		} else {
			return new ReadyDate(left).compareTo(new ReadyDate(right));
		}
	}

	public static void main(final String[] args) {}

	private static class ReadyDate implements Comparable<ReadyDate> {
		private int		month;
		private String	raw;
		private int		year;

		public ReadyDate(final String s) {
			String[] split = s.split(READY_SPLIT);
			if (split.length == 2) {
				try {
					year = Integer.parseInt(split[1]);
					String mon = split[0].substring(0, 3).toUpperCase(Locale.US);
					month = MONTHS.indexOf(mon);
					if ((year > -1) && (month > -1)) {
						return;
					}
				} catch (NumberFormatException e) {
				}
			}
			month = -1;
			year = -1;
			raw = s;
		}

		@Override
		public int compareTo(final ReadyDate other) {
			if (raw == null) {
				if (other.raw == null) {
					final int yearCompare = MiscUtil.intCompare(year, other.year);
					if (yearCompare != 0) {
						return yearCompare;
					} else {
						return MiscUtil.intCompare(month, other.month);
					}
				} else {
					return -1;
				}
			} else {
				if (other.raw == null) {
					return 1;
				} else {
					return stringComparator.compare(raw, other.raw);
				}
			}
		}
	}
}
