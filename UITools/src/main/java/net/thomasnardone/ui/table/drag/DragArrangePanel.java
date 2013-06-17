package net.thomasnardone.ui.table.drag;

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
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import net.thomasnardone.ui.table.MyPanel;

public class DragArrangePanel extends MyPanel {
	private static final Color				PLACEHOLDER_BG		= new Color(216, 96, 96, 128);
	private static final long				serialVersionUID	= 1L;

	private int								currentI;
	private int								currentJ;
	private Component						dragComponent;
	private final DragListener				dragListener;
	private final Map<Component, Component>	dragMap;
	private final JComponent				placeHolder;

	public DragArrangePanel() {
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

	public void addComponent(final JComponent comp, int row) {
		final DragPanel dragPanel = new DragPanel(comp);
		final Component dragComp = dragPanel.getDragComponent();
		dragMap.put(dragComp, dragPanel);
		dragComp.removeMouseListener(dragListener);
		dragComp.removeMouseMotionListener(dragListener);
		dragComp.addMouseListener(dragListener);
		dragComp.addMouseMotionListener(dragListener);

		if (row == (getComponentCount() - 1)) {
			addRow();
		}
		if (row >= getComponentCount()) {
			row = getComponentCount();
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

	private void addRow() {
		super.add(new JPanel(new FlowLayout(FlowLayout.LEFT)));
		validate();
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
			} else {
				cleanupRows();
			}
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

		/**
		 * @param p
		 *            A point relative to <tt>comp</tt>'s parent. See
		 *            {@link SwingUtilities#convertPoint(Component, Point, Component) convertPoint()}.
		 * @return <tt>true</tt> if <tt>comp</tt> contains <tt>p</tt>.
		 */
		private boolean contains(final Point p, final Component comp) {
			return comp.getBounds().contains(p);
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
			dragComponent = new JLabel(loadIcon("drag.png"));
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
