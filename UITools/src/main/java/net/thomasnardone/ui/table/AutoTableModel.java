package net.thomasnardone.ui.table;

import java.text.Format;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import net.thomasnardone.ui.DataType;
import net.thomasnardone.ui.EditType;
import net.thomasnardone.ui.FormatFactory;
import net.thomasnardone.ui.rest.ColumnInfo;
import net.thomasnardone.ui.rest.FormatInfo;
import net.thomasnardone.ui.rest.UpdateInfo;

public class AutoTableModel extends AbstractTableModel {
	private static final long	serialVersionUID	= 1L;
	private final ColumnInfo[]	columns;
	private final Format[]		formats;
	private final String[]		keyFields;
	private final int[]			keyMap;
	private Row[]				rows;

	public AutoTableModel(final List<ColumnInfo> columns, final List<FormatInfo> formats, final List<String> keyFields) {
		this.columns = columns.toArray(new ColumnInfo[columns.size()]);
		this.formats = new Format[columns.size()];
		this.keyFields = keyFields.toArray(new String[keyFields.size()]);
		keyMap = new int[keyFields.size()];
		for (int i = 0; i < keyFields.size(); i++) {
			keyMap[i] = -1;
			String key = keyFields.get(i);
			for (int j = 0; j < columns.size(); j++) {
				if (key.equals(columns.get(j).getName())) {
					keyMap[i] = j;
					break;
				}
			}
			if (keyMap[i] == -1) {
				throw new IllegalArgumentException("Missing ColumnInfo for keyField '" + key + "'");
			}
		}
		Map<DataType, Format> formatMap = new HashMap<>();
		for (FormatInfo info : formats) {
			formatMap.put(info.getDataType(), FormatFactory.getInstance().getFormat(info));
		}
		for (int i = 0; i < this.columns.length; i++) {
			this.formats[i] = formatMap.get(this.columns[i].getDataType());
		}
	}

	public List<UpdateInfo> getChanges() {
		List<UpdateInfo> changes = new ArrayList<>();
		for (Row row : rows) {
			if (row.isDirty()) {
				changes.add(row.getUpdateInfo());
			}
		}
		return changes;
	}

	@Override
	public Class<?> getColumnClass(final int columnIndex) {
		return columns[columnIndex].getDataType().getJavaClass();
	}

	@Override
	public int getColumnCount() {
		return columns.length;
	}

	public String getColumnId(final int column) {
		return columns[column].getName();
	}

	@Override
	public String getColumnName(final int column) {
		return columns[column].getDisplayName();
	}

	public Format getFormat(final int column) {
		return formats[column];
	}

	@Override
	public int getRowCount() {
		return rows == null ? 0 : rows.length;
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		Format format = formats[columnIndex];
		if (format == null) {
			return rows[rowIndex].getValue(columnIndex);
		} else {
			try {
				return format.parseObject(rows[rowIndex].getValue(columnIndex));
			} catch (ParseException e) {
				e.printStackTrace();
				// TODO error handling
				return null;
			}
		}
	}

	@Override
	public boolean isCellEditable(final int rowIndex, final int columnIndex) {
		final boolean ret = !(EditType.ReadOnly.equals(columns[columnIndex].getEditType()));
		return ret;
	}

	public void setData(final String[][] data) {
		rows = new Row[data.length];
		for (int i = 0; i < rows.length; i++) {
			rows[i] = new Row(data[i]);
		}
		fireTableDataChanged();
	}

	@Override
	public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
		Format format = formats[columnIndex];
		if ((format == null) || (aValue instanceof String)) {
			rows[rowIndex].setValue(columnIndex, String.valueOf(aValue));
		} else {
			System.out.println("Formatting " + aValue.getClass());
			rows[rowIndex].setValue(columnIndex, format.format(aValue));
		}
		fireTableCellUpdated(rowIndex, columnIndex);
	}

	private class Row {
		boolean[]	dirty;
		String[]	keyValues;
		String[]	values;

		public Row(final String[] values) {
			this.values = values;
			keyValues = new String[keyMap.length];
			for (int i = 0; i < keyMap.length; i++) {
				keyValues[i] = values[keyMap[i]];
			}
			dirty = new boolean[values.length];
			Arrays.fill(dirty, false);
		}

		public UpdateInfo getUpdateInfo() {
			UpdateInfo info = new UpdateInfo();
			Map<String, String> keyMap = new HashMap<>();
			Map<String, String> valueMap = new HashMap<>();
			for (int i = 0; i < keyValues.length; i++) {
				keyMap.put(keyFields[i], keyValues[i]);
			}
			info.setKeys(keyMap);
			for (int i = 0; i < dirty.length; i++) {
				if (dirty[i]) {
					valueMap.put(columns[i].getName(), values[i]);
				}
			}
			info.setUpdates(valueMap);
			return info;
		}

		public String getValue(final int i) {
			return values[i];
		}

		public boolean isDirty() {
			for (boolean element : dirty) {
				if (element) {
					return true;
				}
			}
			return false;
		}

		public void setValue(final int i, final String value) {
			values[i] = value;
			dirty[i] = true;
		}
	}
}