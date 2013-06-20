package net.thomasnardone.ui.rest;

import java.util.List;

import net.thomasnardone.ui.FilterType;

public class FilterInfo {
	private int				column;
	private String			columnName;
	private String			displayName;
	private int				row;
	private FilterType		type;
	private List<String>	values;

	public FilterInfo() {}

	public int getColumn() {
		return column;
	}

	public String getColumnName() {
		return columnName;
	}

	public String getDisplayName() {
		return displayName;
	}

	public int getRow() {
		return row;
	}

	public FilterType getType() {
		return type;
	}

	public List<String> getValues() {
		return values;
	}

	public void setColumn(final int column) {
		this.column = column;
	}

	public void setColumnName(final String columnName) {
		this.columnName = columnName;
	}

	public void setDisplayName(final String displayName) {
		this.displayName = displayName;
	}

	public void setRow(final int row) {
		this.row = row;
	}

	public void setType(final FilterType type) {
		this.type = type;
	}

	public void setValues(final List<String> values) {
		this.values = values;
	}

}
