package net.thomasnardone.ui.rest;

import net.thomasnardone.ui.DataType;
import net.thomasnardone.ui.EditType;

public class ColumnInfo {
	private DataType	dataType;
	private String		displayName;
	private EditType	editType;
	private String		name;

	public ColumnInfo() {}

	public DataType getDataType() {
		return dataType;
	}

	public String getDisplayName() {
		return displayName;
	}

	public EditType getEditType() {
		return editType;
	}

	public String getName() {
		return name;
	}

	public void setDataType(final DataType dataType) {
		this.dataType = dataType;
	}

	public void setDataType(final String dataType) {
		this.dataType = DataType.valueOf(dataType);
	}

	public void setDisplayName(final String displayName) {
		this.displayName = displayName;
	}

	public void setEditType(final EditType editType) {
		this.editType = editType;
	}

	public void setEditType(final String editType) {
		this.editType = EditType.valueOf(editType);
	}

	public void setName(final String name) {
		this.name = name;
	}
}
