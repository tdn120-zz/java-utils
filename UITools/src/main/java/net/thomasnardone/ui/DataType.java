package net.thomasnardone.ui;

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
