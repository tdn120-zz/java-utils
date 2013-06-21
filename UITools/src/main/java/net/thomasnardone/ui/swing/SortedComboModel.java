package net.thomasnardone.ui.swing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public final class SortedComboModel<T extends Comparable<T>> extends AbstractListModel<T> implements ComboBoxModel<T> {
	private static final long	serialVersionUID	= 1L;

	private final List<T>		items;
	private T					selectedItem;

	public SortedComboModel() {
		super();
		this.items = new ArrayList<>();
	}

	public SortedComboModel(final List<T> items) {
		this();
		if (items != null) {
			for (T item : items) {
				addItem(item);
			}
		}
	}

	public SortedComboModel(final T[] items) {
		this();
		for (T item : items) {
			addItem(item);
		}
	}

	public void addItem(final T item) {
		int pos = Collections.binarySearch(items, item);
		if (pos < 0) {
			items.add((pos + 1) * -1, item);
		} else {
			items.add(pos, item);
		}
	}

	@Override
	public T getElementAt(final int index) {
		return items.get(index);
	}

	@Override
	public Object getSelectedItem() {
		return selectedItem;
	}

	@Override
	public int getSize() {
		return items.size();
	}

	public void removeItem(final T item) {
		int pos = Collections.binarySearch(items, item);
		if (pos > -1) {
			items.remove(pos);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setSelectedItem(final Object anItem) {
		selectedItem = (T) anItem;
	}
}