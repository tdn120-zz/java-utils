package net.thomasnardone.ui.rest;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import net.thomasnardone.ui.DataType;
import net.thomasnardone.ui.EditType;

@XmlRootElement
public class ColumnInfo {
	private DataType			dataType;
	private String				displayName;
	private EditType			editType;
	private String				name;
	private transient String	valueQuery;
	private List<String>		values;

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

	public String getValueQuery() {
		return valueQuery;
	}

	public List<String> getValues() {
		return values;
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

	public void setValueQuery(final String valueQuery) {
		this.valueQuery = valueQuery;
	}

	public void setValues(final List<String> values) {
		this.values = values;
	}

	@Override
	public String toString() {
		return "ColumnInfo [name=" + name + ", displayName=" + displayName + ", dataType=" + dataType + ", editType=" + editType
				+ "]";
	}
}
