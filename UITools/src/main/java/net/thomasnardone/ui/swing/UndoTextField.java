package net.thomasnardone.ui.swing;

import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.Document;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

@SuppressWarnings("serial")
public class UndoTextField extends JTextField {

	public UndoTextField() {
		super();
		setupUndo();
	}

	public UndoTextField(final Document doc, final String text, final int columns) {
		super(doc, text, columns);
		setupUndo();
	}

	public UndoTextField(final int columns) {
		super(columns);
		setupUndo();
	}

	public UndoTextField(final String text) {
		super(text);
		setupUndo();
	}

	public UndoTextField(final String text, final int columns) {
		super(text, columns);
		setupUndo();
	}

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
