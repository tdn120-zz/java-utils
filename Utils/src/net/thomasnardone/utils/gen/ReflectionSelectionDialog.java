package net.thomasnardone.utils.gen;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import net.thomasnardone.utils.StringUtil;

@SuppressWarnings("serial")
public abstract class ReflectionSelectionDialog extends JDialog {
	private static final List<String>	PACKAGE_STARTERS	= Arrays.asList("com", "net", "org");

	private final JList					list;
	private final JButton				okButton;
	private String						selectedItem;

	public ReflectionSelectionDialog(final String title) {
		super((Frame) null, title, true);

		JPanel mainPanel = new JPanel(new BorderLayout());
		list = new JList();
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(final ListSelectionEvent e) {
				okButton.setEnabled(list.getSelectedIndex() > -1);
			}
		});
		mainPanel.add(new JScrollPane(list), BorderLayout.CENTER);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.LINE_AXIS));
		okButton = new JButton(new AbstractAction("OK") {
			@Override
			public void actionPerformed(final ActionEvent e) {
				dispose();
				setItem();
			}
		});
		okButton.setEnabled(false);
		final JButton cancelButton = new JButton(new AbstractAction("Cancel") {
			@Override
			public void actionPerformed(final ActionEvent e) {
				dispose();
				cancel();
			}
		});
		okButton.setPreferredSize(cancelButton.getPreferredSize());
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(okButton);
		buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(cancelButton);
		buttonPanel.add(Box.createHorizontalGlue());
		mainPanel.add(buttonPanel, BorderLayout.SOUTH);

		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
			@Override
			public boolean dispatchKeyEvent(final KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					dispose();
					return true;
				}
				return false;
			}
		});

		setContentPane(mainPanel);
	}

	public String select() {
		if (SwingUtilities.isEventDispatchThread()) {
			throw new IllegalStateException("Cannot block EDT");
		}
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				list.setListData(getItems());
				pack();
				setLocationRelativeTo(null);
				setVisible(true);
			}
		});
		synchronized (this) {
			boolean waiting = true;
			while (waiting) {
				try {
					wait();
					waiting = false;
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		return selectedItem;
	}

	/**
	 * Recursively find items for the dialog's list. Use {@link #updatePackage(String, String)} to increment the package name.
	 */
	protected abstract List<String> findItems(File root, String packageName);

	protected String updatePackage(String packageName, final String name) {
		if ("".equals(packageName)) {
			if (PACKAGE_STARTERS.contains(name)) {
				packageName = name;
			}
		} else {
			packageName += "." + name;
		}
		return packageName;
	}

	private void cancel() {
		synchronized (this) {
			notify();
		}
	}

	private Vector<String> getItems() {
		Vector<String> listItems = new Vector<String>();
		try {
			final Enumeration<URL> resources = ClassLoader.getSystemClassLoader().getResources("");
			while (resources.hasMoreElements()) {
				URL url = resources.nextElement();
				File root = new File(url.getPath());
				listItems.addAll(findItems(root, ""));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		Collections.sort(listItems, StringUtil.comparator());
		return listItems;
	}

	private void setItem() {
		selectedItem = (String) list.getSelectedValue();
		synchronized (this) {
			notify();
		}
	}
}
