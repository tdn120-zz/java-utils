package net.thomasnardone.ui.table.drag;

import java.awt.HeadlessException;

import javax.swing.JLabel;

import net.thomasnardone.ui.swing.MyFrame;

@SuppressWarnings("serial")
public class DragProto extends MyFrame {

	public static void main(final String[] args) {
		new DragProto().setVisible(true);
	}

	public DragProto() throws HeadlessException {
		super(EXIT_ON_CLOSE);
	}

	@Override
	protected void setupFrame() {
		DragArrangePanel mainPanel = new DragArrangePanel();
		addComponent(mainPanel, "One", 0);
		addComponent(mainPanel, "Two", 0);
		addComponent(mainPanel, "Three", 1);
		addComponent(mainPanel, "Four", 1);
		addComponent(mainPanel, "Five", 1);

		setContentPane(mainPanel);
	}

	private void addComponent(final DragArrangePanel panel, final String text, final int row) {
		JLabel label = new JLabel(text);
		panel.addComponent(label, row);
	}

}
