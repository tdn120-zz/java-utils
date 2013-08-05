package net.thomasnardone.ui.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 * Panel that can be used to arrange components via mouse dragging. Since it's all in the same panel, drag and drop, and the
 * serialization it involves, is not required.
 * 
 * @see #addComponent(JComponent, int)
 * @see #getRowCount()
 * @see #getRowComponents(int)
 * @author Thomas Nardone
 */
public class DragArrangePanel extends MyPanel {
	private static final Color				PLACEHOLDER_BG		= new Color(216, 96, 96, 128);
	private static final long				serialVersionUID	= 1L;
	private int								currentI;

	private int								currentJ;
	private Component						dragComponent;
	private final DragListener				dragListener;
	private final Map<Component, Component>	dragMap;
	private final Set<ArrangeListener>		listeners;
	private int								originalI;
	private int								originalJ;
	private final JComponent				placeHolder;

	public DragArrangePanel() {
		listeners = new LinkedHashSet<>();
		dragListener = new DragListener();
		dragMap = new HashMap<>();
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		placeHolder = new JLabel();
		placeHolder.setBorder(BorderFactory.createLineBorder(Color.black));
		placeHolder.setBackground(PLACEHOLDER_BG);
		placeHolder.setOpaque(true);
		addRow();
	}

	/**
	 * Not supported. Use {@link #addComponent(Component, int)}.
	 */
	@Override
	public Component add(final Component comp) {
		throw new UnsupportedOperationException("Use addComponent()");
	}

	/**
	 * Not supported. Use {@link #addComponent(Component, int)}.
	 */
	@Override
	public Component add(final Component comp, final int index) {
		throw new UnsupportedOperationException("Use addComponent()");
	}

	/**
	 * Not supported. Use {@link #addComponent(Component, int)}.
	 */
	@Override
	public void add(final Component comp, final Object constraints) {
		throw new UnsupportedOperationException("Use addComponent()");
	}

	/**
	 * Not supported. Use {@link #addComponent(Component, int)}.
	 */
	@Override
	public void add(final Component comp, final Object constraints, final int index) {
		throw new UnsupportedOperationException("Use addComponent()");
	}

	/**
	 * Not supported. Use {@link #addComponent(Component, int)}.
	 */
	@Override
	public Component add(final String name, final Component comp) {
		throw new UnsupportedOperationException("Use addComponent()");
	}

	public void addArrangeListener(final ArrangeListener listener) {
		listeners.add(listener);
	}

	/**
	 * Add <tt>comp</tt> to the given <tt>row</tt>
	 * 
	 * @param row
	 *            The row index, or -1 to add to the last row.
	 */
	public void addComponent(final JComponent comp, int row) {
		if (row < -1) {
			throw new IllegalArgumentException("Invalid row: " + row);
		}
		final DragPanel dragPanel = new DragPanel(comp);
		final Component dragComp = dragPanel.getDragComponent();
		dragMap.put(dragComp, dragPanel);
		dragComp.removeMouseListener(dragListener);
		dragComp.removeMouseMotionListener(dragListener);
		dragComp.addMouseListener(dragListener);
		dragComp.addMouseMotionListener(dragListener);

		if (row == -1) {
			row = Math.max(0, getComponentCount() - 2);
		}

		if (row == (getComponentCount() - 1)) {
			addRow();
		} else if ((row >= getComponentCount())) {
			row = getComponentCount();
			addRow();
			addRow();
		}
		((JComponent) getComponent(row)).add(dragPanel);
		validate();
	}

	public Component[] getRowComponents(final int row) {
		if (row >= getRowCount()) {
			throw new ArrayIndexOutOfBoundsException(row);
		}

		final JComponent rowComp = (JComponent) getComponent(row);
		Component[] components = null;
		synchronized (rowComp.getTreeLock()) {
			components = rowComp.getComponents();
		}
		for (int i = 0; i < components.length; i++) {
			components[i] = ((DragPanel) components[i]).getContent();
		}
		return components;
	}

	public int getRowCount() {
		return getComponentCount() - 1;
	}

	public void removeArrangeListener(final ArrangeListener listener) {
		listeners.remove(listener);
	}

	public void removeComponent(final JComponent comp) {
		for (int i = 0; i < getComponentCount(); i++) {
			JComponent row = (JComponent) getComponent(i);
			for (int j = 0; j < row.getComponentCount(); j++) {
				DragPanel dp = (DragPanel) row.getComponent(j);
				if (dp.getContent() == comp) {
					row.remove(dp);
					row.validate();
					row.repaint();
				}
			}
		}
		cleanupRows();
		return;
	}

	private void addRow() {
		final JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		super.add(panel);
		validate();
	}

	private void cleanupRows() {
		int i = 0;
		while (i < (getComponentCount() - 1)) {
			if (((JComponent) getComponent(i)).getComponentCount() == 0) {
				remove(i);
			} else {
				i++;
			}
		}
		validate();
		repaint();
	}

	public interface ArrangeListener {
		void componentMoved();
	}

	private final class DragListener extends MouseAdapter {

		@Override
		public void mouseDragged(final MouseEvent e) {
			Point point = SwingUtilities.convertPoint((Component) e.getSource(), e.getPoint(), DragArrangePanel.this);
			for (int i = 0; i < getComponentCount(); i++) {
				JComponent row = (JComponent) getComponent(i);
				if (row.getComponentCount() == 0) {
					movePlaceHolder(row, i, 0);
					return;
				} else if (row.getBounds().contains(point)) {
					final int count = row.getComponentCount();
					for (int j = 0; j < count; j++) {
						final Component nextComp = row.getComponent(j);
						Point rowPoint = SwingUtilities.convertPoint(DragArrangePanel.this, point, row);
						if (contains(rowPoint, nextComp)) {
							movePlaceHolder(row, i, j);
							break;
						} else if ((j == (count - 1)) && past(point, nextComp)) {
							movePlaceHolder(row, i, j);
							break;
						}
					}
					return;
				}
			}
		}

		@Override
		public void mousePressed(final MouseEvent e) {
			for (int i = 0; i < getComponentCount(); i++) {
				JComponent row = (JComponent) getComponent(i);
				for (int j = 0; j < row.getComponentCount(); j++) {
					if (dragMap.get(e.getSource()) == row.getComponent(j)) {
						originalI = i;
						originalJ = j;
						currentI = i;
						currentJ = j;
						dragComponent = dragMap.get(e.getSource());
						setCursor(new Cursor(Cursor.HAND_CURSOR));
						placeHolder.setMinimumSize(dragComponent.getPreferredSize());
						placeHolder.setPreferredSize(dragComponent.getPreferredSize());
						row.add(placeHolder, j);
						dragComponent.setVisible(false);
						row.validate();
						row.repaint();
						return;
					}
				}
			}
		}

		@Override
		public void mouseReleased(final MouseEvent e) {
			setCursor(null);
			final JComponent row = (JComponent) getComponent(currentI);
			final Container oldRow = dragComponent.getParent();
			oldRow.remove(dragComponent);
			dragComponent.setVisible(true);
			row.add(dragComponent, currentJ);
			row.remove(placeHolder);

			oldRow.validate();
			oldRow.repaint();
			if (row != oldRow) {
				row.validate();
				row.repaint();
			}
			if (currentI == (getComponentCount() - 1)) {
				addRow();
			}
			cleanupRows();
			revalidate();
			if ((currentI != originalI) || (currentJ != originalJ)) {
				fireComponentMoved();
			}
		}

		/**
		 * @param p
		 *            A point relative to <tt>comp</tt>'s parent. See
		 *            {@link SwingUtilities#convertPoint(Component, Point, Component) convertPoint()}.
		 * @return <tt>true</tt> if <tt>comp</tt> contains <tt>p</tt>.
		 */
		private boolean contains(final Point p, final Component comp) {
			return comp.getBounds().contains(p);
		}

		private void fireComponentMoved() {
			for (ArrangeListener listener : listeners.toArray(new ArrangeListener[listeners.size()])) {
				listener.componentMoved();
			}
		}

		private void movePlaceHolder(final JComponent row, final int i, final int j) {
			if ((i == currentI) && (j == currentJ)) {
				return;
			}
			final Container oldRow = placeHolder.getParent();
			if (oldRow == null) {
				return;
			} else if (oldRow == row) {
				oldRow.remove(placeHolder);
				try {
					row.add(placeHolder, j);
				} catch (Exception e) {
					System.out.println("Illegal Position: [" + i + ", " + j + "]");
				}
				row.validate();
				row.repaint();
			} else {
				oldRow.remove(placeHolder);
				row.add(placeHolder, j);
				row.validate();
				row.repaint();
				oldRow.validate();
				oldRow.repaint();
			}
			currentI = i;
			currentJ = j;
		}

		/**
		 * @param p
		 *            A point relative to <tt>comp</tt>'s parent. See
		 *            {@link SwingUtilities#convertPoint(Component, Point, Component) convertPoint()}.
		 * @return <tt>true if <tt>p</tt> is on the same row and to the right of <tt>comp</tt>.
		 */
		private boolean past(final Point p, final Component comp) {
			final Rectangle r = comp.getBounds();
			return (p.x > (r.x + r.width)) && (p.y > r.y) && (p.y < (r.y + r.height));
		}
	}

	private class DragPanel extends MyPanel {
		private static final long	serialVersionUID	= 1L;

		private final Component		content;
		private final JLabel		dragComponent;

		public DragPanel(final Component content) {
			super(new FlowLayout(FlowLayout.LEFT));
			this.content = content;
			dragComponent = new JLabel(loadIcon("resources/drag.png"));
			dragComponent.setCursor(new Cursor(Cursor.HAND_CURSOR));
			add(dragComponent);
			add(content);
		}

		public Component getContent() {
			return content;
		}

		public Component getDragComponent() {
			return dragComponent;
		}
	}
}
