package net.thomasnardone.ui.table.drag;

import java.awt.Component;
import java.awt.HeadlessException;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JLabel;

import net.thomasnardone.ui.swing.MyFrame;

@SuppressWarnings("serial")
public class DragProto extends MyFrame {

	public static void main(final String[] args) {
		new DragProto().setVisible(true);
	}

	private DragArrangePanel	mainPanel;

	public DragProto() throws HeadlessException {
		super(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(final WindowEvent e) {
				for (int i = 0; i < mainPanel.getRowCount(); i++) {
					Component[] row = mainPanel.getRowComponents(i);
					for (Component c : row) {
						System.out.print(((JLabel) c).getText());
						System.out.print("\t");
					}
					System.out.println();
				}
				dispose();
			}

		});
	}

	@Override
	protected void setupFrame() {
		mainPanel = new DragArrangePanel();
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
