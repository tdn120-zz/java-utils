package net.thomasnardone.ui.table;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.io.IOException;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TableColumnEditor extends JPanel {
	private static final String[]	DATA_TYPES			= new String[] { "String", "Integer", "Double", "Date" };
	private static final String[]	EDIT_TYPES			= new String[] { "Read Only", "Text", "Combo", "Date" };
	private static final long		serialVersionUID	= 1L;
	private static final int		STRUT				= 5;

	private final JButton			addButton;
	private final String			column;
	private final JComboBox<String>	dataTypeCombo;
	private final JTextField		displayNameField;
	private final JButton			downButton;
	private final JLabel			dragLabel;
	private final JComboBox<String>	editTypeCombo;
	private final JTextField		nameField;
	private final JButton			removeButton;
	private final JButton			upButton;

	public TableColumnEditor(final String column, final Properties props) {
		this.column = column;
		final BoxLayout layout = new BoxLayout(this, BoxLayout.LINE_AXIS);
		setLayout(layout);
		add(Box.createHorizontalStrut(STRUT));
		add(dragLabel = new JLabel(loadIcon("drag.png")));
		add(Box.createHorizontalStrut(STRUT));
		add(upButton = button("up.png"));
		add(Box.createHorizontalStrut(STRUT));
		add(downButton = button("down.png"));
		add(Box.createHorizontalStrut(STRUT));
		add(borderPanel(nameField = new JTextField(10), "Name"));
		add(Box.createHorizontalStrut(STRUT));
		add(borderPanel(displayNameField = new JTextField(10), "Display Name"));
		add(Box.createHorizontalStrut(STRUT));
		add(dataTypeCombo = new JComboBox<>(DATA_TYPES));
		add(Box.createHorizontalStrut(STRUT));
		add(editTypeCombo = new JComboBox<>(EDIT_TYPES));
		add(Box.createHorizontalStrut(STRUT));
		add(addButton = button("add.png"));
		add(Box.createHorizontalStrut(STRUT));
		add(removeButton = button("remove.png"));
		add(Box.createHorizontalStrut(STRUT));

		dataTypeCombo.setBorder(BorderFactory.createTitledBorder("Data Type"));
		editTypeCombo.setBorder(BorderFactory.createTitledBorder("Edit Type"));

		// TODO listeners
		loadColumnProperties(props);
		setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
	}

	private JPanel borderPanel(final JComponent component, final String title) {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		panel.add(component);
		panel.setBorder(BorderFactory.createTitledBorder(title));
		return panel;
	}

	private JButton button(final String icon) {
		JButton button = new JButton(loadIcon(icon));
		button.setMargin(new Insets(1, 1, 1, 1));
		button.setFocusable(false);
		return button;
	}

	private void clear() {
		nameField.setText("");
		displayNameField.setText("");
		dataTypeCombo.setSelectedIndex(-1);
		editTypeCombo.setSelectedIndex(-1);
	}

	private void loadColumnProperties(final Properties props) {
		if (props == null) {
			clear();
		} else {
			nameField.setText(column);
			displayNameField.setText(props.getProperty("column." + column + "." + "displayName"));
			dataTypeCombo.setSelectedItem(props.getProperty("column." + column + "." + "dataType"));
			editTypeCombo.setSelectedItem(props.getProperty("column." + column + "." + "editType"));
		}
	}

	private ImageIcon loadIcon(final String fileName) {
		try {
			return new ImageIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream(fileName)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
