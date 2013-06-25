package net.thomasnardone.ui.swing;

import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class CenterPanel extends JPanel {
	private final GridBagConstraints	cons;

	public CenterPanel() {
		setLayout(new GridBagLayout());
		cons = new GridBagConstraints();
		cons.anchor = GridBagConstraints.CENTER;
	}

	public void setComponent(final Component component) {
		removeAll();
		add(component, cons);
		validate();
		repaint();
	}
}
