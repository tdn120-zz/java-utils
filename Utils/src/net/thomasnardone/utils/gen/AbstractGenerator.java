package net.thomasnardone.utils.gen;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;

public abstract class AbstractGenerator {
	private boolean		inSwitch;
	private int			numTabs	= 0;
	private String		tabs;
	private PrintWriter	writer;

	public AbstractGenerator() {
		tabs = "";
	}

	protected final void closeWriter() {
		if (writer != null) {
			writer.flush();
			writer.close();
		}
	}

	protected final void indent() {
		numTabs++;
		fixTabs();
	}

	protected final void openWriter(final File file) throws FileNotFoundException {
		setupWriter(new PrintWriter(file));
	}

	protected final void openWriter(final PrintStream printStream) {
		setupWriter(new PrintWriter(printStream));
	}

	protected final void openWriter(final String fileName) throws FileNotFoundException {
		setupWriter(new PrintWriter(fileName));
	}

	protected final void undent() {
		numTabs--;
		fixTabs();
	}

	protected final void write(final String s) {
		String realTabs = tabs;
		if (inSwitch && !(s.startsWith("case ") || s.startsWith("default:"))) {
			realTabs += "\t";
		}
		if (s.startsWith("}") && s.endsWith("{")) {
			undent();
			writer.print(tabs + s);
			indent();
		} else if (s.endsWith("{")) {
			writer.print(realTabs + s);
			indent();
			inSwitch = s.startsWith("switch");
		} else if (s.endsWith("}")) {
			undent();
			writer.print(tabs + s);
			inSwitch = false;
		} else if (s.endsWith("});")) {
			undent();
			writer.print(tabs + s);
		} else {
			writer.print(realTabs + s);
		}
	}

	protected final void writeln() {
		writer.println();
	}

	protected final void writeln(final String s) {
		write(s);
		writeln();
	}

	private void fixTabs() {
		StringBuilder newTabs = new StringBuilder();
		for (int i = 0; i < numTabs; i++) {
			newTabs.append("\t");
		}
		tabs = newTabs.toString();
	}

	private void setupWriter(final PrintWriter newWriter) {
		if (writer != null) {
			closeWriter();
		}
		writer = newWriter;
	}
}
