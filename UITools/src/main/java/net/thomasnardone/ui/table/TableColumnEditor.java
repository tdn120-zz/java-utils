package net.thomasnardone.ui.table;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.JToolBar;

import net.thomasnardone.ui.swing.MyComboBox;

public class TableColumnEditor extends JToolBar implements ActionListener {
	public static final String			ADD_ACTION			= "column.add";
	public static final String			DOWN_ACTION			= "column.down";
	public static final String			REMOVE_ACTION		= "column.remove";
	public static final String			UP_ACTION			= "column.up";
	private static final String			DATA_TYPE			= "dataType";
	private static final String[]		DATA_TYPES			= new String[] { "String", "Integer", "Double", "Date" };
	private static final String			DISPLAY_NAME		= "displayName";
	private static final String			EDIT_TYPE			= "editType";
	private static final String[]		EDIT_TYPES			= new String[] { "Read Only", "Text", "Combo", "Date" };

	private static final String			PREFIX				= "column.";
	private static final long			serialVersionUID	= 1L;
	private static final int			STRUT				= 5;
	private final Set<ActionListener>	actionListeners;

	private final JButton				addButton;
	private final String				column;
	private final MyComboBox<String>	dataTypeCombo;
	private final JTextField			displayNameField;
	private final JButton				downButton;
	private final MyComboBox<String>	editTypeCombo;
	private final JTextField			nameField;
	private final JButton				removeButton;
	private final JButton				upButton;

	public TableColumnEditor(final ActionListener listener) {
		this(null, null, listener);
	}

	public TableColumnEditor(final String column, final Properties props, final ActionListener listener) {
		setFloatable(false);
		this.column = column;
		actionListeners = new LinkedHashSet<>();
		add(Box.createHorizontalStrut(STRUT));
		add(upButton = button("up.png"));
		add(Box.createHorizontalStrut(STRUT));
		add(downButton = button("down.png"));
		add(Box.createHorizontalStrut(STRUT));
		add(borderPanel(nameField = new JTextField(10), "Name"));
		add(Box.createHorizontalStrut(STRUT));
		add(borderPanel(displayNameField = new JTextField(10), "Display Name"));
		add(Box.createHorizontalStrut(STRUT));
		add(borderPanel(dataTypeCombo = new MyComboBox<>(DATA_TYPES), "Data Type"));
		add(Box.createHorizontalStrut(STRUT));
		add(borderPanel(editTypeCombo = new MyComboBox<>(EDIT_TYPES), "Edit Type"));
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
		addActionListener(listener);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		e.setSource(this);
		for (ActionListener listener : actionListeners) {
			listener.actionPerformed(e);
		}
	}

	public void addActionListener(final ActionListener listener) {
		actionListeners.add(listener);
	}

	public String getColumnName() {
		return nameField.getText().trim();
	}

	public String getDisplayName() {
		return displayNameField.getText().trim();
	}

	public String getOriginalColumnName() {
		return column;
	}

	public void loadColumnProperties(final Properties props) {
		if (props == null) {
			clear();
		} else {
			nameField.setText(column);
			displayNameField.setText(props.getProperty(PREFIX + column + "." + DISPLAY_NAME));
			dataTypeCombo.setSelectedItem(props.getProperty(PREFIX + column + "." + DATA_TYPE));
			editTypeCombo.setSelectedItem(props.getProperty(PREFIX + column + "." + EDIT_TYPE));
		}
	}

	public void removeActionListener(final ActionListener listener) {
		actionListeners.remove(listener);
	}

	public void saveColumnProperties(final Properties props) {
		if (props == null) {
			return;
		}
		String newColumn = nameField.getText();
		props.setProperty(PREFIX + newColumn + "." + DISPLAY_NAME, getDisplayName());
		if (dataTypeCombo.getSelectedIndex() > -1) {
			props.setProperty(PREFIX + newColumn + "." + DATA_TYPE, dataTypeCombo.getSelectedItem());
		}
		if (editTypeCombo.getSelectedIndex() > -1) {
			props.setProperty(PREFIX + newColumn + "." + EDIT_TYPE, editTypeCombo.getSelectedItem());
		}
	}

	private JComponent borderPanel(final JComponent component, final String title) {
		JToolBar panel = new JToolBar();
		panel.add(component);
		panel.setBorder(BorderFactory.createTitledBorder(title));
		return panel;
	}

	private JButton button(final String icon) {
		JButton button = new JButton(loadIcon(icon));
		button.setMargin(new Insets(1, 1, 1, 1));
		button.setFocusable(false);
		button.setAlignmentY(0.25f);
		return button;
	}

	private void clear() {
		nameField.setText("");
		displayNameField.setText("");
		dataTypeCombo.setSelectedIndex(-1);
		editTypeCombo.setSelectedIndex(-1);
	}

	private ImageIcon loadIcon(final String fileName) {
		try {
			return new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream(fileName)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void setupAction(final AbstractButton button, final String action) {
		button.setActionCommand(action);
		button.addActionListener(this);
	}
}
