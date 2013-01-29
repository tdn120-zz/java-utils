package net.thomasnardone.utils.io;

import java.text.NumberFormat;
import java.util.Locale;

public class ByteFormatter {
	private static final NumberFormat	format	= NumberFormat.getIntegerInstance(Locale.US);
	private static final long			ONE_GIG	= 1073741824l;
	private static final long			ONE_KIL	= 1024l;
	private static final long			ONE_MEG	= 1048576l;

	public static String format(final long bytes) {
		String humanReadable = null;
		if (bytes > ONE_GIG) {
			humanReadable = format.format(bytes / ONE_GIG) + " GB";
		} else if (bytes > ONE_MEG) {
			humanReadable = format.format(bytes / ONE_MEG) + " MB";
		} else if (bytes > ONE_KIL) {
			humanReadable = format.format(bytes / ONE_KIL) + " KB";
		}
		return format.format(bytes) + ((humanReadable == null) ? "" : "\t(" + humanReadable + ")");
	}
}
