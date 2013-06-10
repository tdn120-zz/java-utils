package net.thomasnardone.ui.table;

import java.awt.Dimension;
import java.awt.event.ActionListener;
import java.util.Properties;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JTextField;

import net.thomasnardone.ui.DataType;
import net.thomasnardone.ui.EditType;
import net.thomasnardone.ui.swing.MyComboBox;

public class TableColumnEditor extends MyToolBar implements ActionListener {
	public static final String			ADD_ACTION			= "column.add";
	public static final String			DOWN_ACTION			= "column.down";
	public static final String			EDIT_ACTION			= "column.edit";
	public static final String			EDIT_NAME_ACTION	= "column.edit.name";
	public static final String			REMOVE_ACTION		= "column.remove";
	public static final String			UP_ACTION			= "column.up";
	private static final String			DATA_TYPE			= "dataType";
	private static final String			DISPLAY_NAME		= "displayName";
	private static final String			EDIT_TYPE			= "editType";
	private static final String			PREFIX				= "column.";
	private static final long			serialVersionUID	= 1L;
	private static final int			STRUT				= 5;

	private final JButton				addButton;
	private final String				column;
	private final MyComboBox<DataType>	dataTypeCombo;
	private final JTextField			displayNameField;
	private final JButton				downButton;
	private final MyComboBox<EditType>	editTypeCombo;
	private final JTextField			nameField;
	private final JButton				removeButton;
	private final JButton				upButton;

	public TableColumnEditor(final ActionListener listener) {
		this(null, null, listener);
	}

	public TableColumnEditor(final String column, final Properties props, final ActionListener listener) {
		setFloatable(false);
		this.column = column;
		add(Box.createHorizontalStrut(STRUT));
		add(upButton = button("up.png"));
		add(Box.createHorizontalStrut(STRUT));
		add(downButton = button("down.png"));
		add(Box.createHorizontalStrut(STRUT));
		add(borderPanel(nameField = new JTextField(10), "Name"));
		add(Box.createHorizontalStrut(STRUT));
		add(borderPanel(displayNameField = new JTextField(10), "Display Name"));
		add(Box.createHorizontalStrut(STRUT));
		add(borderPanel(dataTypeCombo = new MyComboBox<>(DataType.values()), "Data Type"));
		add(Box.createHorizontalStrut(STRUT));
		add(borderPanel(editTypeCombo = new MyComboBox<>(EditType.values()), "Edit Type"));
		add(Box.createHorizontalStrut(STRUT));
		add(addButton = button("add.png"));
		add(Box.createHorizontalStrut(STRUT));
		add(removeButton = button("remove.png"));
		add(Box.createHorizontalStrut(STRUT));

		loadColumnProperties(props);
		setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));

		setupAction(addButton, ADD_ACTION);
		setupAction(removeButton, REMOVE_ACTION);
		setupAction(upButton, UP_ACTION);
		setupAction(downButton, DOWN_ACTION);
		setupEditAction(nameField, EDIT_NAME_ACTION);
		setupEditAction(displayNameField, EDIT_ACTION);
		setupSelectAction(dataTypeCombo, EDIT_ACTION);
		setupSelectAction(editTypeCombo, EDIT_ACTION);
		addActionListener(listener);
	}

	public String getColumnName() {
		return nameField.getText().trim();
	}

	public String getDisplayName() {
		return displayNameField.getText().trim();
	}

	public void loadColumnProperties(final Properties props) {
		if (props == null) {
			clear();
		} else {
			nameField.setText(column);
			displayNameField.setText(props.getProperty(PREFIX + column + "." + DISPLAY_NAME));
			dataTypeCombo.setSelectedItem(DataType.valueOf(props.getProperty(PREFIX + column + "." + DATA_TYPE)));
			editTypeCombo.setSelectedItem(EditType.valueOf(props.getProperty(PREFIX + column + "." + EDIT_TYPE)));
		}
	}

	public void saveColumnProperties(final Properties props) {
		if (props == null) {
			return;
		}
		String newColumn = nameField.getText();
		props.setProperty(PREFIX + newColumn + "." + DISPLAY_NAME, getDisplayName());
		if (dataTypeCombo.getSelectedIndex() > -1) {
			props.setProperty(PREFIX + newColumn + "." + DATA_TYPE, dataTypeCombo.getSelectedItem().toString());
		}
		if (editTypeCombo.getSelectedIndex() > -1) {
			props.setProperty(PREFIX + newColumn + "." + EDIT_TYPE, editTypeCombo.getSelectedItem().toString());
		}
	}

	private void clear() {
		nameField.setText("");
		displayNameField.setText("");
		dataTypeCombo.setSelectedIndex(-1);
		editTypeCombo.setSelectedIndex(-1);
	}

}
