package net.thomasnardone.ui.table.editor;

import static net.thomasnardone.ui.table.TableManager.COLUMN_PREFIX;
import static net.thomasnardone.ui.table.TableManager.VALUE_QUERY;

import java.awt.BorderLayout;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import net.thomasnardone.ui.swing.MyPanel;
import net.thomasnardone.ui.swing.UndoTextField;
import net.thomasnardone.ui.table.editor.TableColumnEditor.ColumnNameChangeListener;

@SuppressWarnings("serial")
public class ValueQueryEditor extends MyPanel implements ColumnNameChangeListener {
	private String							columnName;
	private final Set<QueryChangeListener>	listeners;
	private UndoTextField					queryField;

	public ValueQueryEditor(final String columnName) {
		this(columnName, null);
	}

	public ValueQueryEditor(final String columnName, final String query) {
		super(new BorderLayout());
		listeners = new LinkedHashSet<>();
		this.columnName = columnName;
		setBorder(BorderFactory.createTitledBorder(columnName));
		add(queryField = new UndoTextField(), BorderLayout.CENTER);
		setQuery(query);
		queryField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(final DocumentEvent e) {
				fireQueryChanged();
			}

			@Override
			public void insertUpdate(final DocumentEvent e) {
				fireQueryChanged();
			}

			@Override
			public void removeUpdate(final DocumentEvent e) {
				fireQueryChanged();
			}
		});
	}

	public void addQueryChangeListener(final QueryChangeListener listener) {
		listeners.add(listener);
	}

	@Override
	public void columnNameChanged(final String oldName, final String newName) {
		if (columnName.equals(oldName)) {
			columnName = newName;
			setBorder(BorderFactory.createTitledBorder(columnName));
		}
	}

	public String getQuery() {
		return queryField.getText().trim();
	}

	public void removeQueryChangeListener(final QueryChangeListener listener) {
		listeners.remove(listener);
	}

	public void saveQuery(final Properties props) {
		props.setProperty(COLUMN_PREFIX + columnName + "." + VALUE_QUERY, queryField.getText().trim());
	}

	public void setQuery(final String query) {
		queryField.setText(query == null ? "" : query);
	}

	private void fireQueryChanged() {
		for (QueryChangeListener listener : listeners.toArray(new QueryChangeListener[listeners.size()])) {
			listener.queryChanged();
		}
	}

	public interface QueryChangeListener {
		void queryChanged();
	}
}
