package net.thomasnardone.utils.io;

import java.io.IOException;
import java.io.OutputStream;

public class CountingOutputStream extends OutputStream {
	private long				count;
	private final OutputStream	output;

	public CountingOutputStream(final OutputStream output) {
		this.output = output;
		count = 0;
	}

	public long getCount() {
		return count;
	}

	public String getFormattedCount() {
		return ByteFormatter.format(count);
	}

	@Override
	public void write(final int oneByte) throws IOException {
		count++;
		output.write(oneByte);
	}
}
