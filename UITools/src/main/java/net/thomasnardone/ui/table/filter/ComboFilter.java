package net.thomasnardone.ui.table.filter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import net.thomasnardone.ui.rest.FilterInfo;
import net.thomasnardone.ui.swing.MyComboBox;
import net.thomasnardone.ui.swing.SortedComboModel;

@SuppressWarnings("serial")
public class ComboFilter extends AbstractFilter {
	private MyComboBox<String>	combo;

	public ComboFilter(final FilterInfo filterInfo) {
		super(filterInfo);
	}

	@Override
	public boolean include(final Object value) {
		if ("".equals(combo.getSelectedItem())) {
			return true;
		}
		return combo.getSelectedItem().equals(value);
	}

	@Override
	public void updateFilterInfo(final FilterInfo update) {
		super.updateFilterInfo(update);
		reloadItems();
	}

	@Override
	protected void setupFilter() {
		add(combo = new MyComboBox<>());
		reloadItems();
		combo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				fireFilterChanged();
			}
		});
	}

	private void reloadItems() {
		String item = combo.getSelectedItem();
		final List<String> values = new ArrayList<>();
		values.add("");
		List<String> filterValues = getFilterInfo().getValues();
		if (filterValues != null) {
			values.addAll(filterValues);
		}
		combo.setModel(new SortedComboModel<String>(values));
		if (item != null) {
			combo.setSelectedItem(item);
		} else {
			combo.setSelectedItem("");
		}
	}
}
