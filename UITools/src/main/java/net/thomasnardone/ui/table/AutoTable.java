package net.thomasnardone.ui.table;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingWorker;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import net.thomasnardone.ui.rest.AutoTableClient;
import net.thomasnardone.ui.rest.ColumnInfo;
import net.thomasnardone.ui.rest.FilterInfo;
import net.thomasnardone.ui.rest.TableInfo;
import net.thomasnardone.ui.rest.UpdateInfo;
import net.thomasnardone.ui.swing.CenterPanel;
import net.thomasnardone.ui.table.filter.AbstractFilter;
import net.thomasnardone.ui.table.filter.FilterFactory;
import net.thomasnardone.ui.table.filter.FilterListener;

import org.jdesktop.swingx.JXTable;

public class AutoTable extends JPanel implements FilterListener {
	private static final long			serialVersionUID	= 1L;

	private final AutoTableClient		client;
	private final List<AbstractFilter>	filters;

	private AutoTableModel				model;
	private final CenterPanel			progressPanel;
	private final Set<SaveListener>		saveListeners;
	private JScrollPane					scrollPane;

	private final String				serviceName;

	private final JXTable				table;

	public AutoTable(final AutoTableClient client, final String serviceName) {
		this.client = client;
		this.serviceName = serviceName;
		saveListeners = new LinkedHashSet<>();
		progressPanel = new CenterPanel();
		filters = new LinkedList<>();
		table = new JXTable();
		table.setAutoResizeMode(JXTable.AUTO_RESIZE_OFF);
		setLayout(new BorderLayout());
		reloadAll();
	}

	public void addSaveListener(final SaveListener listener) {
		saveListeners.add(listener);
	}

	public void clearFilters() {
		for (AbstractFilter filter : filters) {
			filter.clear();
		}
	}

	@Override
	public void filterChanged() {
		model.fireTableDataChanged();
	}

	public void reload() {
		new DataWorker().execute();
	}

	public void reloadAll() {
		new InfoWorker().execute();
	}

	public void removeSaveListener(final SaveListener listener) {
		saveListeners.remove(listener);
	}

	public void saveChanges() {
		new SaveWorker().execute();
	}

	private void fireSaveFailed() {
		for (SaveListener listener : saveListeners.toArray(new SaveListener[saveListeners.size()])) {
			listener.saveFailed();
		}
	}

	private void fireSaveSuccessful() {
		for (SaveListener listener : saveListeners.toArray(new SaveListener[saveListeners.size()])) {
			listener.saveSuccessful();
		}
	}

	private void initTable(final TableInfo info) {
		model = new AutoTableModel(info.getColumns(), info.getFormats(), info.getKeyFields());
		table.setModel(model);
		for (int i = 0; i < table.getColumnCount(); i++) {
			final TableColumn column = table.getColumn(i);
			column.setCellEditor(EditorFactory.getEditor(info.getColumns().get(i), model.getFormat(i)));
			column.setCellRenderer(new CellRenderer(info.getColumns().get(i)));
		}
		JPanel filterPanel = new JPanel(new GridBagLayout());
		GridBagConstraints cons = new GridBagConstraints();
		cons.weightx = 1.0;
		cons.fill = GridBagConstraints.BOTH;
		filters.clear();
		for (FilterInfo filterInfo : info.getFilters()) {
			cons.gridy = filterInfo.getRow();
			cons.gridx = filterInfo.getColumn();
			final AbstractFilter filter = FilterFactory.getFilter(filterInfo);
			filter.addFilterListener(this);
			filterPanel.add(filter, cons);
			filters.add(filter);
		}
		table.setRowFilter(new AutoRowFilter(filters));
		removeAll();
		add(filterPanel, BorderLayout.NORTH);
		invalidate();
	}

	public interface SaveListener {
		void saveFailed();

		void saveSuccessful();
	}

	private final class CellRenderer implements TableCellRenderer {
		private final Color				bgColor;
		private final Font				italicFont;
		private final Font				normalFont;
		private final TableCellRenderer	renderer;

		public CellRenderer(final ColumnInfo columnInfo) {
			renderer = table.getDefaultRenderer(columnInfo.getDataType().getJavaClass());
			normalFont = getFont().deriveFont(Font.PLAIN);
			italicFont = getFont().deriveFont(Font.ITALIC);
			bgColor = new Color(144, 187, 230);
		}

		@Override
		public Component getTableCellRendererComponent(final JTable table, final Object value, final boolean isSelected,
				final boolean hasFocus, final int row, final int column) {
			final JLabel comp = (JLabel) renderer.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
			final boolean dirty = model.isDirty(row, column);
			if (dirty) {
				comp.setOpaque(true);
				comp.setBackground(bgColor);
				comp.setFont(italicFont);
			} else {
				comp.setFont(normalFont);
			}
			return comp;
		}
	}

	private final class DataWorker extends SwingWorker<String[][], Void> {
		public DataWorker() {
			JProgressBar dataBar = new JProgressBar();
			dataBar.setString("Loading table data");
			dataBar.setStringPainted(true);
			dataBar.setIndeterminate(true);
			progressPanel.setComponent(dataBar);
			if (scrollPane != null) {
				remove(scrollPane);
			}
			add(progressPanel);
			validate();
			repaint();
		}

		@Override
		protected String[][] doInBackground() throws Exception {
			return client.getData(serviceName);
		}

		@Override
		protected void done() {
			try {
				model.setData(get());
				table.packAll();
				scrollPane = new JScrollPane(table);
				remove(progressPanel);
				add(scrollPane, BorderLayout.CENTER);
				revalidate();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
				progressPanel.setComponent(new JLabel("Error occurred, please check logs."));
				revalidate();
			}
		}
	}

	private final class InfoWorker extends SwingWorker<TableInfo, Void> {
		public InfoWorker() {
			removeAll();
			final JProgressBar progressBar = new JProgressBar();
			progressBar.setString("Loading table info...");
			progressBar.setStringPainted(true);
			progressBar.setIndeterminate(true);
			progressPanel.setComponent(progressBar);
			add(progressPanel, BorderLayout.CENTER);
			validate();
			repaint();
		}

		@Override
		protected TableInfo doInBackground() throws Exception {
			return client.getTableInfo(serviceName);
		}

		@Override
		protected void done() {
			try {
				TableInfo info = get();
				initTable(info);
				reload();
			} catch (InterruptedException | ExecutionException | IllegalArgumentException e) {
				e.printStackTrace();
				progressPanel.setComponent(new JLabel("Error occurred, please check logs."));
				revalidate();
			}
		}
	}

	private class SaveWorker extends SwingWorker<Boolean, Void> {
		private final List<UpdateInfo>	changes;

		public SaveWorker() {
			changes = model.getChanges();
		}

		@Override
		protected Boolean doInBackground() throws Exception {
			return client.updateTable(serviceName, changes);
		}

		@Override
		protected void done() {
			Boolean success;
			try {
				success = get();
			} catch (InterruptedException | ExecutionException e) {
				e.printStackTrace();
				success = false;
			}
			if (success) {
				model.resetDirty();
				fireSaveSuccessful();
			} else {
				fireSaveFailed();
			}
		}
	}
}
