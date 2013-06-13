package net.thomasnardone.ui.swing;

import java.awt.GraphicsConfiguration;
import java.awt.HeadlessException;

import javax.swing.JFrame;

/**
 * Version of JFrame that handles some basics - set the close operation, pack, and center the frame in the window.
 * 
 * @author Thomas Nardone
 */
public abstract class MyFrame extends JFrame {
	private static final long	serialVersionUID	= 1L;

	public MyFrame() {
		this(EXIT_ON_CLOSE);
	}

	public MyFrame(final GraphicsConfiguration gc, final int defaultCloseOperation) {
		super(gc);
		construct(defaultCloseOperation);
	}

	public MyFrame(final int defaultCloseOperation) throws HeadlessException {
		construct(defaultCloseOperation);
	}

	public MyFrame(final String title) {
		this(title, EXIT_ON_CLOSE);
	}

	public MyFrame(final String title, final GraphicsConfiguration gc, final int defaultCloseOperation) {
		super(title, gc);
		construct(defaultCloseOperation);
	}

	public MyFrame(final String title, final int defaultCloseOperation) throws HeadlessException {
		super(title);
		construct(defaultCloseOperation);
	}

	/**
	 * Create the frame - add components, setup listeners, etc.
	 */
	protected abstract void setupFrame();

	private void construct(final int closeOperation) {
		setupFrame();
		setDefaultCloseOperation(closeOperation);
		pack();
		setLocationRelativeTo(null);
	}
}
