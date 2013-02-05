package net.thomasnardone.utils.io;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "response")
public class Response {
	private String	message;
	private boolean	ok;
	private Object	payload;

	public Response() {

	}

	public Response(final boolean ok) {
		this(ok, null, null);
	}

	public Response(final boolean ok, final String message) {
		this(ok, message, null);
	}

	public Response(final boolean ok, final String message, final Object payload) {
		this.ok = ok;
		this.message = message;
		this.payload = payload;
	}

	public String getMessage() {
		return message;
	}

	public Object getPayload() {
		return payload;
	}

	public boolean isOk() {
		return ok;
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public void setOk(final boolean ok) {
		this.ok = ok;
	}

	public void setPayload(final Object payload) {
		this.payload = payload;
	}

	@Override
	public String toString() {
		return "Response [message=" + message + ", ok=" + ok + ", payload=" + payload + "]";
	}
}
