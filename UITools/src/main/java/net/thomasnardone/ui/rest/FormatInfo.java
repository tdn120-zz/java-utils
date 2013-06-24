package net.thomasnardone.ui.rest;

import javax.xml.bind.annotation.XmlRootElement;

import net.thomasnardone.ui.DataType;

@XmlRootElement
public class FormatInfo {
	private DataType	dataType;
	private String		format;

	public FormatInfo() {}

	public FormatInfo(final DataType dataType, final String format) {
		this.dataType = dataType;
		this.format = format;
	}

	public DataType getDataType() {
		return dataType;
	}

	public String getFormat() {
		return format;
	}

	public void setDataType(final DataType dataType) {
		this.dataType = dataType;
	}

	public void setFormat(final String format) {
		this.format = format;
	}
}
