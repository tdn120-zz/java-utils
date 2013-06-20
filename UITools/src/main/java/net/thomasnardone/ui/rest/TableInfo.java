package net.thomasnardone.ui.rest;

import java.util.List;

public class TableInfo {
	private List<ColumnInfo>	columns;
	private List<FilterInfo>	filters;
	private List<FormatInfo>	formats;

	public TableInfo() {}

	public List<ColumnInfo> getColumns() {
		return columns;
	}

	public List<FilterInfo> getFilters() {
		return filters;
	}

	public List<FormatInfo> getFormats() {
		return formats;
	}

	public void setColumns(final List<ColumnInfo> columns) {
		this.columns = columns;
	}

	public void setFilters(final List<FilterInfo> filters) {
		this.filters = filters;
	}

	public void setFormats(final List<FormatInfo> formats) {
		this.formats = formats;
	}
}
