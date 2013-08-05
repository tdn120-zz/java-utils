package net.thomasnardone.ui.table.editor;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.thomasnardone.ui.DataType;
import net.thomasnardone.ui.EditType;
import net.thomasnardone.ui.swing.MyComboBox;
import net.thomasnardone.ui.swing.MyPanel;
import net.thomasnardone.ui.table.TableManager;

public class TableColumnEditor extends MyPanel implements ActionListener {
	public static final String					ADD_ACTION			= "column.add";
	public static final String					DOWN_ACTION			= "column.down";
	public static final String					EDIT_ACTION			= "column.edit";
	public static final String					FILTER_ACTION		= "column.toggleFilter";
	public static final String					REMOVE_ACTION		= "column.remove";
	public static final String					UP_ACTION			= "column.up";
	public static final String					VALUE_QUERY_ACTION	= "column.toggleValueQuery";
	private static final long					serialVersionUID	= 1L;
	private static final int					STRUT				= 5;

	private final JButton						addButton;
	private final String						column;
	private final MyComboBox<DataType>			dataTypeCombo;
	private final JTextField					displayNameField;
	private final JButton						downButton;
	private final MyComboBox<EditType>			editTypeCombo;
	private final JToggleButton					filterButton;
	private final Set<ColumnNameChangeListener>	nameChangeListeners;
	private final JTextField					nameField;
	private final JButton						removeButton;
	private final JButton						upButton;
	private final JToggleButton					valueButton;

	public TableColumnEditor() {
		this(null, null);
	}

	public TableColumnEditor(final String column, final Properties props) {
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		nameChangeListeners = new LinkedHashSet<>();
		this.column = column;
		add(Box.createHorizontalStrut(STRUT));
		add(upButton = button("resources/up.png", "Move Up"));
		add(Box.createHorizontalStrut(STRUT));
		add(downButton = button("resources/down.png", "Move Down"));
		add(Box.createHorizontalStrut(STRUT));
		add(borderPanel(nameField = new JTextField(10), "Name"));
		add(Box.createHorizontalStrut(STRUT));
		add(borderPanel(displayNameField = new JTextField(10), "Display Name"));
		add(Box.createHorizontalStrut(STRUT));
		add(borderPanel(dataTypeCombo = new MyComboBox<>(DataType.values()), "Data Type"));
		add(Box.createHorizontalStrut(STRUT));
		add(borderPanel(editTypeCombo = new MyComboBox<>(EditType.values()), "Edit Type"));
		add(Box.createHorizontalStrut(STRUT));
		add(filterButton = toggleButton("resources/filter.png", "Toggle Filter"));
		add(Box.createHorizontalStrut(STRUT));
		add(valueButton = toggleButton("resources/values.png", "Toggle Value Query"));
		add(Box.createHorizontalStrut(STRUT));
		add(addButton = button("resources/add.png", "Add Column"));
		add(Box.createHorizontalStrut(STRUT));
		add(removeButton = button("resources/remove.png", "Remove Column"));
		add(Box.createHorizontalStrut(STRUT));

		loadColumnProperties(props);
		setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));

		setupAction(addButton, ADD_ACTION);
		setupAction(removeButton, REMOVE_ACTION);
		setupAction(upButton, UP_ACTION);
		setupAction(downButton, DOWN_ACTION);
		setupAction(filterButton, FILTER_ACTION);
		setupAction(valueButton, VALUE_QUERY_ACTION);
		setupEditAction(nameField, EDIT_ACTION);
		setupEditAction(displayNameField, EDIT_ACTION);
		setupSelectAction(dataTypeCombo, EDIT_ACTION);
		setupSelectAction(editTypeCombo, EDIT_ACTION);
		nameField.getDocument().addDocumentListener(new NameChangeListener());
	}

	public void addColumnNameChangeListener(final ColumnNameChangeListener cncl) {
		nameChangeListeners.add(cncl);
	}

	public String getColumnName() {
		return nameField.getText().trim();
	}

	public String getDisplayName() {
		return displayNameField.getText().trim();
	}

	public boolean isFilterOn() {
		return filterButton.isSelected();
	}

	public boolean isValueQueryOn() {
		return valueButton.isSelected();
	}

	public void loadColumnProperties(final Properties props) {
		if (props == null) {
			clear();
		} else {
			nameField.setText(column);
			displayNameField.setText(props.getProperty(TableManager.COLUMN_PREFIX + column + "." + TableManager.DISPLAY_NAME));
			dataTypeCombo.setSelectedItem(DataType.valueOf(props.getProperty(TableManager.COLUMN_PREFIX + column + "."
					+ TableManager.DATA_TYPE)));
			editTypeCombo.setSelectedItem(EditType.valueOf(props.getProperty(TableManager.COLUMN_PREFIX + column + "."
					+ TableManager.EDIT_TYPE)));
			final String filterType = props.getProperty(TableManager.FILTER + "." + column + "." + TableManager.TYPE);
			filterButton.setSelected(filterType != null);
			String valueQuery = props.getProperty(TableManager.COLUMN_PREFIX + column + "." + TableManager.VALUE_QUERY);
			valueButton.setSelected(valueQuery != null);
		}
	}

	public void removeColumnNameChangeListener(final ColumnNameChangeListener cncl) {
		nameChangeListeners.remove(cncl);
	}

	public void saveColumnProperties(final Properties props) {
		if (props == null) {
			return;
		}
		String newColumn = nameField.getText();
		props.setProperty(TableManager.COLUMN_PREFIX + newColumn + "." + TableManager.DISPLAY_NAME, getDisplayName());
		if (dataTypeCombo.getSelectedIndex() > -1) {
			props.setProperty(TableManager.COLUMN_PREFIX + newColumn + "." + TableManager.DATA_TYPE, dataTypeCombo
					.getSelectedItem().toString());
		}
		if (editTypeCombo.getSelectedIndex() > -1) {
			props.setProperty(TableManager.COLUMN_PREFIX + newColumn + "." + TableManager.EDIT_TYPE, editTypeCombo
					.getSelectedItem().toString());
		}
	}

	private void clear() {
		nameField.setText("");
		displayNameField.setText("");
		dataTypeCombo.setSelectedIndex(-1);
		editTypeCombo.setSelectedIndex(-1);
	}

	private void fireNameChange(final String oldName, final String newName) {
		for (ColumnNameChangeListener cncl : nameChangeListeners
				.toArray(new ColumnNameChangeListener[nameChangeListeners.size()])) {
			cncl.columnNameChanged(oldName, newName);
		}
	}

	public interface ColumnNameChangeListener {
		void columnNameChanged(String oldName, String newName);
	}

	private final class NameChangeListener implements DocumentListener {
		private String	currentName;

		public NameChangeListener() {
			currentName = nameField.getText();
		}

		@Override
		public void changedUpdate(final DocumentEvent e) {
			updateName();
		}

		@Override
		public void insertUpdate(final DocumentEvent e) {
			updateName();
		}

		@Override
		public void removeUpdate(final DocumentEvent e) {
			updateName();
		}

		private void updateName() {
			String oldName = currentName;
			currentName = nameField.getText();
			if (!(oldName.equals(currentName))) {
				fireNameChange(oldName, currentName);
			}
		}
	}
}
