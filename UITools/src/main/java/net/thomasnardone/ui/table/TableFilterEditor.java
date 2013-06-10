package net.thomasnardone.ui.table;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import net.thomasnardone.ui.FilterType;
import net.thomasnardone.ui.swing.MyComboBox;
import net.thomasnardone.ui.swing.SortedComboModel;

public class TableFilterEditor extends MyToolBar {
	public static final String				ADD_ACTION			= "filter.add";

	public static final String				DOWN_ACTION			= "filter.down";
	public static final String				EDIT_ACTION			= "filter.edit";
	public static final String				REMOVE_ACTION		= "filter.remove";
	public static final String				UP_ACTION			= "filter.up";
	private static final long				serialVersionUID	= 1L;
	private final JButton					addButton;

	private final MyComboBox<String>		columnCombo;
	private final JButton					downButton;
	private final JButton					removeButton;
	private final TableEditor				tableEditor;
	private final MyComboBox<FilterType>	typeCombo;
	private final JButton					upButton;

	public TableFilterEditor(final TableEditor tableEditor) {
		this.tableEditor = tableEditor;
		add(upButton = button("up.png"));
		add(downButton = button("down.png"));
		add(borderPanel(columnCombo = new MyComboBox<>(), "Column"));
		add(borderPanel(typeCombo = new MyComboBox<>(), "Type"));
		add(Box.createHorizontalGlue());
		add(addButton = button("add.png"));
		add(removeButton = button("remove.png"));

		setupSelectAction(columnCombo, EDIT_ACTION);
		setupSelectAction(typeCombo, EDIT_ACTION);
		setupAction(addButton, ADD_ACTION);
		setupAction(removeButton, REMOVE_ACTION);
		setupAction(upButton, UP_ACTION);
		setupAction(downButton, DOWN_ACTION);

		columnCombo.addPopupMenuListener(new PopupMenuListener() {
			@Override
			public void popupMenuCanceled(final PopupMenuEvent e) {}

			@Override
			public void popupMenuWillBecomeInvisible(final PopupMenuEvent e) {}

			@Override
			public void popupMenuWillBecomeVisible(final PopupMenuEvent e) {
				columnCombo.setModel(new SortedComboModel<>(tableEditor.getColumnNames()));
			}
		});
	}

	public void columnChanged(final String oldName, final String newName) {
		if ((oldName != null) && oldName.equals(columnCombo.getSelectedItem())) {
			columnCombo.setSelectedItem(newName);
		}
	}
}
