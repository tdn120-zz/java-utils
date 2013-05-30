package net.thomasnardone.ui.table;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.filechooser.FileFilter;

public class TableEditor extends JFrame {
	private static final long	serialVersionUID	= 1L;

	public static void main(final String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		final String propFile = args.length > 0 ? args[0] : null;
		SwingUtilities.invokeAndWait(new Runnable() {
			public void run() {
				try {
					new TableEditor(propFile).setVisible(true);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}

	private final Properties	props;

	public TableEditor(final String propFile) throws IOException {
		super("Table Editor");
		setDefaultCloseOperation(EXIT_ON_CLOSE);

		props = new Properties();
		loadProperties(propFile);

		JPanel mainPanel = new JPanel();
		// TODO
		mainPanel.add(new JButton("Click me I'm awesome!"));
		setContentPane(mainPanel);
		pack();
		setLocationRelativeTo(null);
	}

	private void loadProperties(final String propFile) throws IOException, FileNotFoundException {
		if (propFile == null) {
			final File file = selectPropFile();
			if (file == null) {
				return;
			}
			props.load(new FileInputStream(file));
		} else {
			props.load(new FileInputStream(propFile));
		}
	}

	private File selectPropFile() {
		JFileChooser cf = new JFileChooser();
		cf.setFileFilter(new FileFilter() {
			@Override
			public boolean accept(final File f) {
				final String filename = f.getName().toLowerCase();
				return filename.endsWith("properties") || filename.endsWith("props");
			}

			@Override
			public String getDescription() {
				return "Properties Files (*.props, *.properties)";
			}
		});
		cf.showOpenDialog(this);
		return cf.getSelectedFile();
	}

}
