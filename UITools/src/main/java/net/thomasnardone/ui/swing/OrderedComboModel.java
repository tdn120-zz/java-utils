package net.thomasnardone.ui.swing;

import java.util.ArrayList;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ComboBoxModel;

public class OrderedComboModel<T> extends AbstractListModel<T> implements ComboBoxModel<T> {
	private static final long	serialVersionUID	= 1L;

	private final List<T>		items;
	private T					selectedItem;

	public OrderedComboModel() {
		this.items = new ArrayList<>();
	}

	public OrderedComboModel(final List<T> items) {
		this();
		if (items != null) {
			for (T item : items) {
				addItem(item);
			}
		}
	}

	public OrderedComboModel(final T[] items) {
		this();
		for (T item : items) {
			addItem(item);
		}
	}

	public void addItem(final T item) {
		items.add(item);
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
		items.remove(item);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setSelectedItem(final Object anItem) {
		selectedItem = (T) anItem;
	}
}
