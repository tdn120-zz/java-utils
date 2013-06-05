package net.thomasnardone.ui.swing;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.text.Document;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

public class UndoTextArea extends JTextArea {
	private static final long	serialVersionUID	= 1L;

	public UndoTextArea() {
		super();
		setupUndo();
	}

	public UndoTextArea(final Document doc) {
		super(doc);
		setupUndo();
	}

	public UndoTextArea(final Document doc, final String text, final int rows, final int columns) {
		super(doc, text, rows, columns);
		setupUndo();
	}

	public UndoTextArea(final int rows, final int columns) {
		super(rows, columns);
		setupUndo();
	}

	public UndoTextArea(final String text) {
		super(text);
		setupUndo();
	}

	public UndoTextArea(final String text, final int rows, final int columns) {
		super(text, rows, columns);
		setupUndo();
	}

	@SuppressWarnings("serial")
	private void setupUndo() {
		final UndoManager undoManager = new UndoManager();
		getDocument().addUndoableEditListener(undoManager);
		InputMap im = getInputMap(WHEN_FOCUSED);
		ActionMap am = getActionMap();
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Z, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Undo");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_Y, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()), "Redo");
		am.put("Undo", new AbstractAction() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					if (undoManager.canUndo()) {
						undoManager.undo();
					}
				} catch (CannotUndoException exp) {
					exp.printStackTrace();
				}
			}
		});
		am.put("Redo", new AbstractAction() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					if (undoManager.canRedo()) {
						undoManager.redo();
					}
				} catch (CannotUndoException exp) {
					exp.printStackTrace();
				}
			}
		});
	}
}
