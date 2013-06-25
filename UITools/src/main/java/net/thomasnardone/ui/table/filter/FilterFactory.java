package net.thomasnardone.ui.table.filter;

import net.thomasnardone.ui.rest.FilterInfo;

public class FilterFactory {

	public static AbstractFilter getFilter(final FilterInfo filterInfo) {
		switch (filterInfo.getType()) {
			case Combo:
				return new ComboFilter(filterInfo);
			case Text:
				return new TextFilter(filterInfo);
			default:
				return new TextFilter(filterInfo);
		}
	}
}
