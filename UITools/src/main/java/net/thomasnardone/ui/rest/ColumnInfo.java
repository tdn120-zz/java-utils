package net.thomasnardone.ui.rest;

public class ColumnInfo {
	private String	dataType;
	private String	displayName;
	private String	editType;
	private String	name;

	public ColumnInfo() {}

	public String getDataType() {
		return dataType;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getEditType() {
		return editType;
	}

	public String getName() {
		return name;
	}

	public void setDataType(final String dataType) {
		this.dataType = dataType;
	}

	public void setDisplayName(final String displayName) {
		this.displayName = displayName;
	}

	public void setEditType(final String editType) {
		this.editType = editType;
	}

	public void setName(final String name) {
		this.name = name;
	}
}
