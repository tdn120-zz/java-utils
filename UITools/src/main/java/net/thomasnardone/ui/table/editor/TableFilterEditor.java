package net.thomasnardone.ui.table.editor;

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
import net.thomasnardone.ui.swing.MyPanel;
import net.thomasnardone.ui.swing.SortedComboModel;
import net.thomasnardone.ui.table.ColumnManager;

public class TableFilterEditor extends MyPanel {
	public static final String				EDIT_ACTION			= "filter.edit";
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
		final String type = props.getProperty(ColumnManager.FILTER + "." + columnName + "." + ColumnManager.TYPE);
		if (type != null) {
			typeCombo.setSelectedItem(FilterType.valueOf(type));
		} else {
			typeCombo.setSelectedIndex(-1);
		}
	}

	public void saveFilterProperties(final Properties props) {
		props.put(ColumnManager.FILTER + "." + columnName + "." + ColumnManager.TYPE, typeCombo.getSelectedItem().toString());
	}
}
