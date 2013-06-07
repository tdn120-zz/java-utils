package net.thomasnardone.ui.rest;

import java.util.List;

public class TableInfo {
	private List<ColumnInfo>	columns;
	private List<FormatInfo>	formats;

	public TableInfo() {}

	public List<ColumnInfo> getColumns() {
		return columns;
	}

	public List<FormatInfo> getFormats() {
		return formats;
	}

	public void setColumns(final List<ColumnInfo> columns) {
		this.columns = columns;
	}

	public void setFormats(final List<FormatInfo> formats) {
		this.formats = formats;
	}
}
