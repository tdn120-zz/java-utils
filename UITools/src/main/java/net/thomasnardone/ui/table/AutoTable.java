package net.thomasnardone.ui.table;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;

import net.thomasnardone.ui.rest.AutoTableClient;
import net.thomasnardone.ui.rest.FilterInfo;
import net.thomasnardone.ui.rest.TableInfo;
import net.thomasnardone.ui.rest.UpdateInfo;
import net.thomasnardone.ui.swing.CenterPanel;
import net.thomasnardone.ui.table.filter.AbstractFilter;
import net.thomasnardone.ui.table.filter.FilterFactory;
import net.thomasnardone.ui.table.filter.FilterListener;

import org.jdesktop.swingx.JXTable;

public class AutoTable extends JPanel implements FilterListener {
	private static final long		serialVersionUID	= 1L;

	private final AutoTableClient	client;

	private AutoTableModel			model;
	private final CenterPanel		progressPanel;
	private JScrollPane				scrollPane;
	private final String			serviceName;

	private final JXTable			table;

	public AutoTable(final AutoTableClient client, final String serviceName) {
		this.client = client;
		this.serviceName = serviceName;
		progressPanel = new CenterPanel();
		table = new JXTable();
		table.setAutoResizeMode(JXTable.AUTO_RESIZE_OFF);
		setLayout(new BorderLayout());
		reloadAll();
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

	public void saveChanges() {
		new SaveWorker().execute();
	}

	private void fireSaveSuccessful(final boolean success) {
		// TODO
	}

	private void initTable(final TableInfo info) {
		model = new AutoTableModel(info.getColumns(), info.getFormats(), info.getKeyFields());
		table.setModel(model);
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumn(i).setCellEditor(EditorFactory.getEditor(info.getColumns().get(i), model.getFormat(i)));
		}
		JPanel filterPanel = new JPanel(new GridBagLayout());
		GridBagConstraints cons = new GridBagConstraints();
		cons.weightx = 1.0;
		cons.fill = GridBagConstraints.BOTH;
		List<AbstractFilter> filters = new LinkedList<>();
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
			revalidate();
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
			final JProgressBar progressBar = new JProgressBar();
			progressBar.setString("Loading table info...");
			progressBar.setStringPainted(true);
			progressBar.setIndeterminate(true);
			progressPanel.setComponent(progressBar);
			add(progressPanel, BorderLayout.CENTER);
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
				// TODO error handling?
				e.printStackTrace();
				success = false;
			}
			fireSaveSuccessful(success);
		}
	}
}
