package net.thomasnardone.ui.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;

import net.thomasnardone.ui.FilterType;
import net.thomasnardone.ui.swing.MyComboBox;
import net.thomasnardone.ui.swing.SortedComboModel;

public class TableFilterEditor extends MyPanel {
	public static final String				DOWN_ACTION			= "filter.down";
	public static final String				EDIT_ACTION			= "filter.edit";
	public static final String				LEFT_ACTION			= "filter.left";
	public static final String				RIGHT_ACTION		= "filter.right";
	public static final String				UP_ACTION			= "filter.up";
	private static final long				serialVersionUID	= 1L;

	private final JComponent				borderPanel;
	private String							columnName;
	private final JButton					downButton;
	private final JButton					leftButton;
	private final JButton					rightButton;
	private final MyComboBox<FilterType>	typeCombo;
	private final JButton					upButton;

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
		JPanel movePanel = new JPanel(new BorderLayout(0, 0));
		movePanel.add(buttonPanel(leftButton = button("left_small.png", "Move Left")), BorderLayout.WEST);
		movePanel.add(buttonPanel(rightButton = button("right_small.png", "Move Right")), BorderLayout.EAST);
		movePanel.add(buttonPanel(upButton = button("up_small.png", "Move Up")), BorderLayout.NORTH);
		movePanel.add(buttonPanel(downButton = button("down_small.png", "Move Down")), BorderLayout.SOUTH);
		mainPanel.add(movePanel);
		borderPanel = borderPanel(mainPanel, columnName);
		add(borderPanel);
		add(Box.createHorizontalGlue());

		setupSelectAction(typeCombo, EDIT_ACTION);
		setupAction(upButton, UP_ACTION);
		setupAction(downButton, DOWN_ACTION);
		setupAction(leftButton, LEFT_ACTION);
		setupAction(rightButton, RIGHT_ACTION);
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

	private JComponent buttonPanel(final JButton... buttons) {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 0));
		for (JButton button : buttons) {
			panel.add(button);
		}
		return panel;
	}
}
