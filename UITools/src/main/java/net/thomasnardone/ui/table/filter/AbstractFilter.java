package net.thomasnardone.ui.table.filter;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import net.thomasnardone.ui.rest.FilterInfo;

@SuppressWarnings("serial")
public abstract class AbstractFilter extends JPanel {
	private FilterInfo					filterInfo;
	private final Set<FilterListener>	listeners;

	public AbstractFilter(final FilterInfo filterInfo) {
		listeners = new LinkedHashSet<>();
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		this.filterInfo = filterInfo;
		setupFilter();
		setBorder(BorderFactory.createTitledBorder(filterInfo.getDisplayName()));
	}

	public void addFilterListener(final FilterListener listener) {
		listeners.add(listener);
	}

	public String getColumnName() {
		return filterInfo.getColumnName();
	}

	public abstract boolean include(String value);

	public void removeFilterListener(final FilterListener listener) {
		listeners.remove(listener);
	}

	public void updateFilterInfo(final FilterInfo update) {
		filterInfo = update;
	}

	protected final void fireFilterChanged() {
		for (FilterListener listener : listeners.toArray(new FilterListener[listeners.size()])) {
			listener.filterChanged();
		}
	}

	protected FilterInfo getFilterInfo() {
		return filterInfo;
	}

	protected abstract void setupFilter();
}
