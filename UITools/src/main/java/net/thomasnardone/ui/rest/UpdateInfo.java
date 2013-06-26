package net.thomasnardone.ui.rest;

import java.util.Map;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UpdateInfo {
	Map<String, String>	keys;
	Map<String, String>	updates;

	public UpdateInfo() {}

	public Map<String, String> getKeys() {
		return keys;
	}

	public Map<String, String> getUpdates() {
		return updates;
	}

	public void setKeys(final Map<String, String> keys) {
		this.keys = keys;
	}

	public void setUpdates(final Map<String, String> updates) {
		this.updates = updates;
	}

	@Override
	public String toString() {
		return "UpdateInfo [keys=" + keys + ", updates=" + updates + "]";
	}
}
