package net.thomasnardone.ui.table;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.RowFilter;
import javax.swing.table.TableModel;

import net.thomasnardone.ui.table.filter.AbstractFilter;

public class AutoRowFilter extends RowFilter<TableModel, Integer> {
	private final Map<String, AbstractFilter>	filterMap;

	public AutoRowFilter(final List<AbstractFilter> filters) {
		filterMap = new HashMap<>();
		for (AbstractFilter filter : filters) {
			filterMap.put(filter.getColumnName(), filter);
		}
	}

	@Override
	public boolean include(final Entry<? extends TableModel, ? extends Integer> entry) {
		AutoTableModel model = (AutoTableModel) entry.getModel();
		for (int i = 0; i < model.getColumnCount(); i++) {
			final String column = model.getColumnId(i);
			final AbstractFilter filter = filterMap.get(column);
			if ((filter != null) && !filter.include(entry.getStringValue(i))) {
				return false;
			}
		}
		return true;
	}
}