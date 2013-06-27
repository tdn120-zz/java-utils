package net.thomasnardone.ui.table.filter;

import java.util.regex.Pattern;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.thomasnardone.ui.rest.FilterInfo;

@SuppressWarnings("serial")
public class TextFilter extends AbstractFilter {
	private JTextField	field;
	private Pattern		pattern;

	public TextFilter(final FilterInfo filterInfo) {
		super(filterInfo);
	}

	@Override
	public void clear() {
		field.setText("");

	}

	@Override
	public boolean include(final String value) {
		return (pattern == null) || pattern.matcher(value.toLowerCase()).matches();
	}

	@Override
	protected void setupFilter() {
		add(field = new JTextField(10));
		field.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(final DocumentEvent e) {
				filter();
			}

			@Override
			public void insertUpdate(final DocumentEvent e) {
				filter();
			}

			@Override
			public void removeUpdate(final DocumentEvent e) {
				filter();
			}
		});
	}

	private void filter() {
		if (field.getText().trim().isEmpty()) {
			pattern = null;
		} else {
			String text = field.getText().toLowerCase().replaceAll("\\*", ".*");
			if (text.endsWith(" ")) {
				text = text.substring(0, text.length() - 1);
			} else if (!text.endsWith(".*")) {
				text = text + ".*";
			}
			pattern = Pattern.compile(text);
		}
		fireFilterChanged();
	}
}
