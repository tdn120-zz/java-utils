package net.thomasnardone.ui.table;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.prefs.Preferences;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

public class TableEditor extends JFrame implements ActionListener {
	private static final Preferences	prefs				= Preferences.userNodeForPackage(TableEditor.class);
	private static final long			serialVersionUID	= 1L;

	public static void main(final String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		final String propFile = args.length > 0 ? args[0] : null;
		SwingUtilities.invokeAndWait(new Runnable() {
			@Override
			public void run() {
				try {
					new TableEditor(propFile).setVisible(true);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private final JPanel		columnPanel;
	private final Properties	props;

	public TableEditor(final String propFile) throws IOException {
		super("Table Editor");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		props = new Properties();

		columnPanel = new JPanel();
		columnPanel.setLayout(new BoxLayout(columnPanel, BoxLayout.PAGE_AXIS));
		JPanel mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(new JScrollPane(columnPanel), BorderLayout.CENTER);
		setContentPane(mainPanel);

		JMenuBar mb = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		fileMenu.add(menuItem("Open", "open", KeyEvent.VK_O, KeyEvent.VK_O));
		fileMenu.add(menuItem("Save", "save", KeyEvent.VK_S, KeyEvent.VK_S));
		fileMenu.add(menuItem("Exit", "exit", KeyEvent.VK_X, KeyEvent.VK_Q));
		mb.add(fileMenu);
		setJMenuBar(mb);

		final TableColumnEditor sizingPanel = new TableColumnEditor(null, null);
		columnPanel.add(sizingPanel);
		pack();
		columnPanel.remove(sizingPanel);

		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		if ("open".equals(e.getActionCommand())) {
			open();
		} else if ("save".equals(e.getActionCommand())) {
			save();
		} else if ("exit".equals(e.getActionCommand())) {
			dispose();
		}
	}

	private JMenuItem menuItem(final String text, final String action, final int mnemonic) {
		return menuItem(text, action, mnemonic, -1);
	}

	private JMenuItem menuItem(final String text, final String action, final int mnemonic, final int ctrl_key) {
		JMenuItem item = new JMenuItem(text, mnemonic);
		item.setActionCommand(action);
		if (ctrl_key > -1) {
			item.setAccelerator(KeyStroke.getKeyStroke(ctrl_key, KeyEvent.CTRL_DOWN_MASK));
		}
		item.addActionListener(this);
		return item;
	}

	private void open() {
		File file = selectPropFile();

		try {
			props.load(new FileInputStream(file));
			String[] columns = props.getProperty("columns").split(" ");
			for (String column : columns) {
				if (column.trim().length() < 1) {
					continue;
				}
				columnPanel.add(new TableColumnEditor(column, props));
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		columnPanel.validate();
	}

	private void save() {
		// TODO
	}

	private File selectPropFile() {
		JFileChooser cf = new JFileChooser(prefs.get("lastDir", null));
		cf.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(final File f) {
				final String filename = f.getName().toLowerCase();
				return f.isDirectory() || filename.endsWith("properties") || filename.endsWith("props");
			}

			@Override
			public String getDescription() {
				return "Properties Files (*.props, *.properties)";
			}
		});
		cf.setDialogTitle("Select Table Properties File");
		cf.showOpenDialog(this);
		final File file = cf.getSelectedFile();
		prefs.put("lastDir", file.getParent());
		return file;
	}

}
