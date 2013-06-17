package net.thomasnardone.ui.table;

import java.awt.Color;
import java.util.Properties;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.thomasnardone.ui.FilterType;
import net.thomasnardone.ui.swing.MyComboBox;
import net.thomasnardone.ui.swing.SortedComboModel;

public class TableFilterEditor extends MyPanel {
	public static final String				EDIT_ACTION			= "filter.edit";
	public static final String				TYPE				= "type";
	private static final long				serialVersionUID	= 1L;

	private final JComponent				borderPanel;
	private String							columnName;
	private final MyComboBox<FilterType>	typeCombo;

	public TableFilterEditor(final String columnName) {
		this.columnName = columnName;
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		setBackground(new Color(0, 0, 0, 0));
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
		mainPanel.setBackground(new Color(0, 0, 0, 0));
		mainPanel.add(new JLabel("Type:"));
		mainPanel.add(typeCombo = new MyComboBox<>(new SortedComboModel<>(FilterType.values())));
		typeCombo.setMaximumSize(typeCombo.getPreferredSize());
		borderPanel = borderPanel(mainPanel, columnName);
		add(borderPanel);
		add(Box.createHorizontalGlue());

		setupSelectAction(typeCombo, EDIT_ACTION);
	}

	public void columnChanged(final String oldName, final String newName) {
		if (columnName.equals(oldName)) {
			borderPanel.setBorder(BorderFactory.createTitledBorder(newName));
			columnName = newName;
		}
	}

	public String getColumnName() {
		return columnName;
	}

	public void loadFilterProperties(final Properties props) {
		final String type = props.getProperty(TableEditor.FILTER + "." + columnName + "." + TYPE);
		if (type != null) {
			typeCombo.setSelectedItem(FilterType.valueOf(type));
		} else {
			typeCombo.setSelectedIndex(-1);
		}
	}

	public void saveFilterProperties(final Properties props) {
		props.put(TableEditor.FILTER + "." + columnName + "." + TYPE, typeCombo.getSelectedItem().toString());
	}
}
