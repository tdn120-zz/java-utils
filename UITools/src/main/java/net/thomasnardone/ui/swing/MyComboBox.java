package net.thomasnardone.ui.swing;

import java.util.Vector;

import javax.swing.ComboBoxModel;
import javax.swing.JComboBox;

/**
 * JComboBox that returns E on getSelectedItem(), rather than Object. This can result in errors if the combo box is made editable.
 * 
 * @author Thomas Nardone
 */
public class MyComboBox<E> extends JComboBox<E> {
	private static final long	serialVersionUID	= 1L;

	public MyComboBox() {
		super();
	}

	public MyComboBox(final ComboBoxModel<E> aModel) {
		super(aModel);
	}

	public MyComboBox(final E[] items) {
		super(items);
	}

	public MyComboBox(final Vector<E> items) {
		super(items);
	}

	@SuppressWarnings("unchecked")
	@Override
	public E getSelectedItem() {
		return (E) super.getSelectedItem();
	}
}
