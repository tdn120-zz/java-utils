package net.thomasnardone.ui;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.SimpleDateFormat;

import net.thomasnardone.ui.rest.FormatInfo;

public class FormatFactory {
	public static FormatFactory getInstance() {
		return SingletonHolder.INSTANCE;
	}

	private FormatFactory() {}

	public Format getFormat(final FormatInfo info) {
		DataType type = info.getDataType();
		String pattern = info.getFormat();
		switch (type) {
			case Date:
				return new SimpleDateFormat(pattern);

			case Double:
				return new DecimalFormat(pattern);

			case Integer:
				return new DecimalFormat(pattern);

			default:
				return null;
		}
	}

	private static class SingletonHolder {
		public static final FormatFactory	INSTANCE	= new FormatFactory();
	}
}
