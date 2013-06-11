package net.thomasnardone.ui.table.drag;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

import javax.swing.JPanel;

public class PanelTransferable implements Transferable {

	private final DataFlavor[]	flavors	= new DataFlavor[] { PanelDataFlavor.SHARED_INSTANCE };
	private final JPanel		panel;

	public PanelTransferable(final JPanel panel) {
		this.panel = panel;
	}

	public JPanel getPanel() {

		return panel;

	}

	@Override
	public Object getTransferData(final DataFlavor flavor) throws UnsupportedFlavorException, IOException {

		Object data = null;
		if (isDataFlavorSupported(flavor)) {

			data = getPanel();

		} else {

			throw new UnsupportedFlavorException(flavor);

		}

		return data;

	}

	@Override
	public DataFlavor[] getTransferDataFlavors() {
		return flavors;
	}

	@Override
	public boolean isDataFlavorSupported(final DataFlavor flavor) {

		// Okay, for this example, this is over kill, but makes it easier
		// to add new flavor support by subclassing
		boolean supported = false;

		for (DataFlavor mine : getTransferDataFlavors()) {

			if (mine.equals(flavor)) {

				supported = true;
				break;

			}

		}

		return supported;

	}

}