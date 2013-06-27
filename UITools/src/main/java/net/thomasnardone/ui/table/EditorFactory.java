package net.thomasnardone.ui.table;

import java.text.Format;
import java.text.NumberFormat;

import javax.swing.DefaultCellEditor;
import javax.swing.table.TableCellEditor;

import net.thomasnardone.ui.rest.ColumnInfo;
import net.thomasnardone.ui.swing.MyComboBox;
import net.thomasnardone.ui.swing.OrderedComboModel;

import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.table.DatePickerCellEditor;
import org.jdesktop.swingx.table.NumberEditorExt;

public class EditorFactory {
	public static TableCellEditor getEditor(final ColumnInfo columnInfo, final Format format) {
		switch (columnInfo.getEditType()) {
			case Combo:
				MyComboBox<String> combo = new MyComboBox<String>(new OrderedComboModel<>(columnInfo.getValues()));
				return new DefaultCellEditor(combo);
			case Date:
				return new DatePickerCellEditor();

			case ReadOnly:
				return null;

			case Text:
			default:
				switch (columnInfo.getDataType()) {
					case Double:
					case Integer:
						return new NumberEditorExt((NumberFormat) format);
					case Date:
					case String:
					default:
						return new JXTable.GenericEditor();
				}
		}
	}

	private EditorFactory() {}
}
