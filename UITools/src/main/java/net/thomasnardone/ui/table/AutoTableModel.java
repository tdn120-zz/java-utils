package net.thomasnardone.ui.table;

import java.text.Format;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.table.AbstractTableModel;

import net.thomasnardone.ui.DataType;
import net.thomasnardone.ui.FormatFactory;
import net.thomasnardone.ui.rest.ColumnInfo;
import net.thomasnardone.ui.rest.FormatInfo;

public class AutoTableModel extends AbstractTableModel {
	private static final String	READ_ONLY			= "Read Only";
	private static final long	serialVersionUID	= 1L;
	private final ColumnInfo[]	columns;
	private final Format[]		formats;
	private String[][]			rawData;

	public AutoTableModel(final List<ColumnInfo> columns, final List<FormatInfo> formats) {
		this.columns = columns.toArray(new ColumnInfo[columns.size()]);
		this.formats = new Format[columns.size()];
		Map<DataType, Format> formatMap = new HashMap<>();
		for (FormatInfo info : formats) {
			formatMap.put(info.getDataType(), FormatFactory.getInstance().getFormat(info));
		}
		for (int i = 0; i < this.columns.length; i++) {
			this.formats[i] = formatMap.get(this.columns[i].getDataType());
		}
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
		return rawData == null ? 0 : rawData.length;
	}

	@Override
	public Object getValueAt(final int rowIndex, final int columnIndex) {
		Format format = formats[columnIndex];
		if (format == null) {
			return rawData[rowIndex][columnIndex];
		} else {
			try {
				return format.parseObject(rawData[rowIndex][columnIndex]);
			} catch (ParseException e) {
				e.printStackTrace();
				// TODO error handling
				return null;
			}
		}
	}

	@Override
	public boolean isCellEditable(final int rowIndex, final int columnIndex) {
		return !(READ_ONLY.equals(columns[columnIndex].getEditType()));
	}

	public void setData(final String[][] data) {
		rawData = data;
		fireTableDataChanged();
	}

	@Override
	public void setValueAt(final Object aValue, final int rowIndex, final int columnIndex) {
		Format format = formats[columnIndex];
		if (format == null) {
			rawData[rowIndex][columnIndex] = String.valueOf(aValue);
		} else {
			rawData[rowIndex][columnIndex] = format.format(aValue);
		}
		fireTableCellUpdated(rowIndex, columnIndex);
	}
}