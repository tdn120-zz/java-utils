package net.thomasnardone.ui.table.drag;

import java.awt.HeadlessException;

import javax.swing.JPanel;

import net.thomasnardone.ui.swing.MyFrame;

@SuppressWarnings("serial")
public class DragProto extends MyFrame {

	public static void main(final String[] args) {
		new DragProto().runFrame("Drag Prototype");
	}

	public DragProto() throws HeadlessException {
		super(EXIT_ON_CLOSE);
	}

	@Override
	protected void setupFrame() {
		JPanel mainPanel = new DragParentPanel();
		mainPanel.add(new DragPanel("One"));
		mainPanel.add(new DragPanel("Two"));
		mainPanel.add(new DragPanel("Three"));

		setContentPane(mainPanel);
	}

}
