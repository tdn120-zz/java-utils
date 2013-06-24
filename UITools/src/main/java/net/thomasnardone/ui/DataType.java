package net.thomasnardone.ui;

import javax.xml.bind.annotation.XmlEnum;

@XmlEnum
public enum DataType {
	Date(java.util.Date.class), Double(Double.class), Integer(Integer.class), String(String.class);

	private final Class<?>	javaClass;

	private DataType(final Class<?> javaClass) {
		this.javaClass = javaClass;
	}

	public Class<?> getJavaClass() {
		return javaClass;
	}

}
