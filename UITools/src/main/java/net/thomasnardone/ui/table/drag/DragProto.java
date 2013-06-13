package net.thomasnardone.ui.table.drag;

import java.awt.HeadlessException;

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
		DragParentPanel mainPanel = new DragParentPanel();
		addComponent(mainPanel, "One", 0);
		addComponent(mainPanel, "Two", 0);
		addComponent(mainPanel, "Three", 1);
		addComponent(mainPanel, "Four", 1);
		addComponent(mainPanel, "Five", 1);

		setContentPane(mainPanel);
	}

	private void addComponent(final DragParentPanel panel, final String text, final int row) {
		DragPanel dp = new DragPanel(text);
		panel.addComponent(dp, dp.getDragComponent(), row);
	}

}
