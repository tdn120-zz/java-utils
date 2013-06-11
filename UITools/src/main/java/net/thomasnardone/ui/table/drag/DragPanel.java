package net.thomasnardone.ui.table.drag;

import java.awt.dnd.DragGestureRecognizer;

import javax.swing.JLabel;

import net.thomasnardone.ui.table.MyPanel;

public class DragPanel extends MyPanel {

	private static final long		serialVersionUID	= 1L;

	private DragGestureRecognizer	dgr;

	private DragGestureHandler		dragGestureHandler;

	public DragPanel(final String text) {
		add(new JLabel(loadIcon("drag.png")));
		add(new JLabel(text));
		// setDropTarget(dropTarget);
	}

	// @Override
	// public void addNotify() {
	//
	// super.addNotify();
	//
	// if (dgr == null) {
	//
	// dragGestureHandler = new DragGestureHandler(this);
	// dgr = DragSource.getDefaultDragSource().createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_MOVE,
	// dragGestureHandler);
	//
	// }
	//
	// }
	//
	// @Override
	// public void removeNotify() {
	//
	// if (dgr != null) {
	//
	// dgr.removeDragGestureListener(dragGestureHandler);
	// dragGestureHandler = null;
	//
	// }
	//
	// dgr = null;
	//
	// super.removeNotify();
	//
	// }
}
