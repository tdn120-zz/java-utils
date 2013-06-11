package net.thomasnardone.ui.table.drag;

import java.awt.Container;
import java.awt.Cursor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DragGestureEvent;
import java.awt.dnd.DragGestureListener;
import java.awt.dnd.DragSource;
import java.awt.dnd.DragSourceDragEvent;
import java.awt.dnd.DragSourceDropEvent;
import java.awt.dnd.DragSourceEvent;
import java.awt.dnd.DragSourceListener;

import javax.swing.JPanel;

public class DragGestureHandler implements DragGestureListener, DragSourceListener {

	private final JPanel	child;
	private Container		parent;

	public DragGestureHandler(final JPanel child) {

		this.child = child;

	}

	@Override
	public void dragDropEnd(final DragSourceDropEvent dsde) {

		// If the drop was not sucessful, we need to
		// return the component back to it's previous
		// parent
		if (!dsde.getDropSuccess()) {

			getParent().add(getPanel());

			getParent().invalidate();
			getParent().repaint();

		}
	}

	@Override
	public void dragEnter(final DragSourceDragEvent dsde) {}

	@Override
	public void dragExit(final DragSourceEvent dse) {}

	@Override
	public void dragGestureRecognized(final DragGestureEvent dge) {

		// When the drag begins, we need to grab a reference to the
		// parent container so we can return it if the drop
		// is rejected
		Container parent = getPanel().getParent();

		setParent(parent);

		// Create our transferable wrapper
		Transferable transferable = new PanelTransferable(getPanel());

		// Start the "drag" process...
		DragSource ds = dge.getDragSource();
		ds.startDrag(dge, Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR), transferable, this);

		// Remove the panel from the parent. If we don't do this, it
		// can cause serialization issues. We could over come this
		// by allowing the drop target to remove the component, but that's
		// an argument for another day
		parent.remove(getPanel());

		// Update the display
		parent.invalidate();
		parent.repaint();

	}

	@Override
	public void dragOver(final DragSourceDragEvent dsde) {}

	@Override
	public void dropActionChanged(final DragSourceDragEvent dsde) {}

	public JPanel getPanel() {
		return child;
	}

	public Container getParent() {
		return parent;
	}

	public void setParent(final Container parent) {
		this.parent = parent;
	}
}