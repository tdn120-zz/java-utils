package net.thomasnardone.ui.table.drag;

import java.awt.Component;
import java.awt.Cursor;
import java.awt.FlowLayout;

import javax.swing.JLabel;

import net.thomasnardone.ui.table.MyPanel;

public class DragPanel extends MyPanel {

	private static final long	serialVersionUID	= 1L;

	private final JLabel		dragComponent;

	private final String		text;

	public DragPanel(final String text) {
		super(new FlowLayout(FlowLayout.LEFT));
		this.text = text;
		dragComponent = new JLabel(loadIcon("drag.png"));
		dragComponent.setCursor(new Cursor(Cursor.HAND_CURSOR));
		add(dragComponent);
		add(new JLabel(text));
	}

	public Component getDragComponent() {
		return dragComponent;
	}

	public String getText() {
		return text;
	}
}
