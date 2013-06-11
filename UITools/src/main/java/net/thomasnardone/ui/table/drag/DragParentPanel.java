package net.thomasnardone.ui.table.drag;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import net.thomasnardone.ui.table.MyPanel;

public class DragParentPanel extends MyPanel {
	private static final long	serialVersionUID	= 1L;

	private int					currentIndex;

	public DragParentPanel() {
		addMouseListener(new DragListener());
	}

	@Override
	public Component add(final Component comp) {
		comp.addMouseListener(new DragListener());
		return super.add(comp);
	}

	private final class DragListener extends MouseAdapter {

		@Override
		public void mouseDragged(final MouseEvent e) {
			System.out.print(".");
		}

		@Override
		public void mouseMoved(final MouseEvent e) {
			if (currentIndex > -1) {
				System.out.print(".");
			}
		}

		@Override
		public void mousePressed(final MouseEvent e) {
			if (e.getSource() == DragParentPanel.this) {
				return;
			}
			for (int i = 0; i < getComponentCount(); i++) {
				if (e.getSource() == getComponent(i)) {
					currentIndex = i;
					System.out.println("Current index: " + currentIndex);
					return;
				}
			}
		}

		@Override
		public void mouseReleased(final MouseEvent e) {
			currentIndex = -1;
			System.out.println();
		}
	}

}
