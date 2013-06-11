package net.thomasnardone.ui.table.drag;

import java.awt.datatransfer.DataFlavor;

import javax.swing.JPanel;

public class PanelDataFlavor extends DataFlavor {
	public static final PanelDataFlavor	SHARED_INSTANCE	= new PanelDataFlavor();

	public PanelDataFlavor() {

		super(JPanel.class, null);

	}

}