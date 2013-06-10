package net.thomasnardone.ui.table;

import javax.swing.JFrame;
import javax.swing.JScrollPane;

import net.thomasnardone.ui.rest.DummyTableClient;
import net.thomasnardone.ui.rest.TableInfo;

import org.jdesktop.swingx.JXTable;

public class DummyTable extends JFrame {
	private static final long	serialVersionUID	= 1L;

	public static void main(final String[] args) {
		new DummyTable().setVisible(true);
	}

	public DummyTable() {
		super("Dummy Table");
		final DummyTableClient client = new DummyTableClient();
		final TableInfo info = client.getTableInfo("");
		final AutoTableModel model = new AutoTableModel(info.getColumns(), info.getFormats());
		model.loadData(client.getData(null));
		JXTable table = new JXTable(model);
		table.packAll();
		JScrollPane mainPane = new JScrollPane(table);
		setContentPane(mainPane);
		pack();
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
