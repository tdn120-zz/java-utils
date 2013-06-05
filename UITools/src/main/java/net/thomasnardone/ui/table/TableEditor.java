package net.thomasnardone.ui.table;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.prefs.Preferences;

import javax.swing.BoxLayout;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

public class TableEditor extends JFrame implements ActionListener {
	private static final String			COLUMNS				= "columns";
	private static final String			EXIT				= "exit";
	private static final String			NEW					= "new";
	private static final String			OPEN				= "open";
	private static final Preferences	prefs				= Preferences.userNodeForPackage(TableEditor.class);
	private static final String			SAVE				= "save";
	private static final String			SAVE_AS				= "save_as";
	private static final long			serialVersionUID	= 1L;
	private static final String			TITLE				= "Table Editor";

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
	private boolean				dirty;
	private final JPanel		mainPanel;
	private File				propFile;
	private final Properties	props;

	public TableEditor(final String propFile) throws IOException {
		super(TITLE);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(final WindowEvent e) {
				exit();
			}
		});

		props = new Properties();

		columnPanel = new JPanel();
		columnPanel.setLayout(new BoxLayout(columnPanel, BoxLayout.PAGE_AXIS));
		mainPanel = new JPanel(new BorderLayout());
		mainPanel.add(new JScrollPane(columnPanel), BorderLayout.CENTER);
		setContentPane(mainPanel);

		JMenuBar mb = new JMenuBar();
		JMenu fileMenu = new JMenu("File");
		fileMenu.setMnemonic(KeyEvent.VK_F);
		fileMenu.add(menuItem("New", NEW, KeyEvent.VK_N, KeyEvent.VK_N));
		fileMenu.add(menuItem("Open", OPEN, KeyEvent.VK_O, KeyEvent.VK_O));
		fileMenu.add(menuItem("Save", SAVE, KeyEvent.VK_S, KeyEvent.VK_S));
		fileMenu.add(menuItem("Save As", SAVE_AS, KeyEvent.VK_A, KeyEvent.VK_S, true));
		fileMenu.add(menuItem("Exit", EXIT, KeyEvent.VK_X, KeyEvent.VK_Q));
		mb.add(fileMenu);
		setJMenuBar(mb);

		final TableColumnEditor sizingPanel = new TableColumnEditor(this);
		columnPanel.add(sizingPanel);
		pack();
		columnPanel.remove(sizingPanel);
		setSize(new Dimension(getSize().width, 480));

		setLocationRelativeTo(null);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		final String action = e.getActionCommand();
		if (NEW.equals(action)) {
			newFile();
		} else if (OPEN.equals(action)) {
			open();
		} else if (SAVE.equals(action)) {
			save();
		} else if (SAVE_AS.equals(action)) {
			saveAs();
		} else if (EXIT.equals(action)) {
			dispose();
		} else if (TableColumnEditor.ADD_ACTION.equals(action)) {
			addColumn((TableColumnEditor) e.getSource());
		} else if (TableColumnEditor.REMOVE_ACTION.equals(action)) {
			removeColumn((TableColumnEditor) e.getSource());
		} else if (TableColumnEditor.UP_ACTION.equals(action)) {
			moveUp((TableColumnEditor) e.getSource());
		} else if (TableColumnEditor.DOWN_ACTION.equals(action)) {
			moveDown((TableColumnEditor) e.getSource());
		}

	}

	private void addColumn(final TableColumnEditor source) {
		for (int i = 0; i < columnPanel.getComponentCount(); i++) {
			if (source == columnPanel.getComponent(i)) {
				columnPanel.add(new TableColumnEditor(this), i + 1);
				columnPanel.validate();
				setDirty();
				return;
			}
		}
	}

	private void exit() {
		if (dirty) {
			final int response = JOptionPane.showConfirmDialog(this, "Would you like to save your changes?", "Exit",
					JOptionPane.YES_NO_CANCEL_OPTION);
			switch (response) {
				case JOptionPane.YES_OPTION:
					save();
					break;
				case JOptionPane.NO_OPTION:
					break;
				case JOptionPane.CANCEL_OPTION:
					return;
			}
		}
		dispose();
	}

	private JMenuItem menuItem(final String text, final String action, final int mnemonic, final int ctrlKey) {
		return menuItem(text, action, mnemonic, ctrlKey, false);
	}

	private JMenuItem menuItem(final String text, final String action, final int mnemonic, final int ctrlKey, final boolean shift) {
		JMenuItem item = new JMenuItem(text, mnemonic);
		item.setActionCommand(action);
		if (shift) {
			item.setAccelerator(KeyStroke.getKeyStroke(ctrlKey, KeyEvent.CTRL_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK));
		} else {
			item.setAccelerator(KeyStroke.getKeyStroke(ctrlKey, KeyEvent.CTRL_DOWN_MASK));
		}
		item.addActionListener(this);
		return item;
	}

	private void moveDown(final TableColumnEditor source) {
		for (int i = 0; i < columnPanel.getComponentCount(); i++) {
			if (source == columnPanel.getComponent(i)) {
				if (i < (columnPanel.getComponentCount() - 1)) {
					columnPanel.remove(source);
					columnPanel.add(source, i + 1);
					columnPanel.validate();
					setDirty();
				}
				return;
			}
		}
	}

	private void moveUp(final TableColumnEditor source) {
		for (int i = 0; i < columnPanel.getComponentCount(); i++) {
			if (source == columnPanel.getComponent(i)) {
				if (i > 0) {
					columnPanel.remove(source);
					columnPanel.add(source, i - 1);
					columnPanel.validate();
					setDirty();
				}
				return;
			}
		}
	}

	private void newFile() {
		if (dirty) {
			String message = "Would you like to save your changes first?";
			if (propFile != null) {
				message = "Would you like to save your changes to " + propFile.getName() + " first?";
			}
			final int response = JOptionPane.showConfirmDialog(this, message, "New File", JOptionPane.YES_NO_CANCEL_OPTION);
			switch (response) {
				case JOptionPane.YES_OPTION:
					save();
				case JOptionPane.NO_OPTION:
					startNewFile();
					break;

				case JOptionPane.CANCEL_OPTION:
					break;
			}
		} else {
			startNewFile();
		}
	}

	private void open() {
		propFile = selectPropFile("Open", "Open");
		if (propFile == null) {
			return;
		}

		try {
			props.load(new FileInputStream(propFile));
			String[] columns = props.getProperty(COLUMNS).split(" ");
			for (String column : columns) {
				if (column.trim().length() < 1) {
					continue;
				}
				columnPanel.add(new TableColumnEditor(column, props, this));
			}
		} catch (IOException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(this, "Exception occurred: " + e.toString(), "Error loading properties",
					JOptionPane.ERROR_MESSAGE);
			columnPanel.removeAll();
		}
		columnPanel.validate();
		reset();
	}

	private void removeColumn(final TableColumnEditor source) {
		columnPanel.remove(source);
		columnPanel.validate();
		columnPanel.repaint();
		setDirty();
	}

	private void reset() {
		dirty = false;
		if (propFile == null) {
			setTitle(TITLE);
		} else {
			setTitle(TITLE + " - " + propFile.getName());
		}
	}

	private void save() {
		if (propFile == null) {
			saveAs();
		} else {
			props.clear();
			StringBuilder columns = new StringBuilder();
			for (int i = 0; i < columnPanel.getComponentCount(); i++) {
				TableColumnEditor column = (TableColumnEditor) columnPanel.getComponent(i);
				columns.append(column.getColumnName());
				if (i < (columnPanel.getComponentCount() - 1)) {
					columns.append(" ");
				}
				column.saveColumnProperties(props);
			}
			props.setProperty(COLUMNS, columns.toString());
			try {
				final FileOutputStream output = new FileOutputStream(propFile);
				props.store(output, "Created by " + getClass().getName());
				output.flush();
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(this, "Unable to Save", "Exception occurred: " + e.toString(),
						JOptionPane.ERROR_MESSAGE);
			}
			reset();
		}
	}

	private void saveAs() {
		File newFile = selectPropFile("Save As", "Save");
		while (newFile.exists() && ((propFile == null) || !newFile.equals(propFile))) {
			final int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to overwrite " + newFile.getName()
					+ "?", "Overwrite " + newFile.getName() + "?", JOptionPane.YES_NO_CANCEL_OPTION);
			if (response == JOptionPane.YES_OPTION) {
				break;
			} else if (response == JOptionPane.CANCEL_OPTION) {
				return;
			} else {
				newFile = selectPropFile("Save As", "Save");
			}
		}
		propFile = newFile;
		if (propFile != null) {
			save();
		}
	}

	private File selectPropFile(final String title, final String actionText) {
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
		cf.setDialogTitle(title);
		cf.showDialog(this, actionText);
		final File file = cf.getSelectedFile();
		if (file != null) {
			prefs.put("lastDir", file.getParent());
		}
		return file;
	}

	private void setDirty() {
		if (!dirty) {
			dirty = true;
			setTitle(getTitle() + "*");
		}
	}

	private void startNewFile() {
		propFile = null;
		columnPanel.removeAll();
		columnPanel.add(new TableColumnEditor(this));
		columnPanel.validate();
		reset();
		setDirty();
	}
}
