package net.thomasnardone.ui.table;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;

import net.thomasnardone.ui.rest.DummyTableClient;
import net.thomasnardone.ui.rest.FilterInfo;
import net.thomasnardone.ui.rest.TableInfo;
import net.thomasnardone.ui.swing.MyFrame;
import net.thomasnardone.ui.table.filter.AbstractFilter;
import net.thomasnardone.ui.table.filter.FilterFactory;
import net.thomasnardone.ui.table.filter.FilterListener;

import org.jdesktop.swingx.JXTable;

public class AutoTable extends JPanel implements FilterListener {
	private static final long	serialVersionUID	= 1L;

	public static void main(final String[] args) {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception e) {
		}
		@SuppressWarnings("serial")
		MyFrame frame = new MyFrame("Table", JFrame.EXIT_ON_CLOSE) {
			@Override
			protected void setupFrame() {
				setContentPane(new AutoTable());
			}
		};
		frame.setVisible(true);
	}

	private final AutoTableModel	model;
	private final JXTable			table;

	public AutoTable() {
		final DummyTableClient client = new DummyTableClient();
		final TableInfo info = client.getTableInfo("");
		model = new AutoTableModel(info.getColumns(), info.getFormats());
		model.loadData(client.getData(null));
		table = new JXTable(model);

		table.setAutoResizeMode(JXTable.AUTO_RESIZE_OFF);
		table.packAll();
		JScrollPane scrollPane = new JScrollPane(table);
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

		setLayout(new BorderLayout());
		add(filterPanel, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
	}

	@Override
	public void filterChanged() {
		model.fireTableDataChanged();
	}
}
