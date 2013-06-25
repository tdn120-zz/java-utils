package net.thomasnardone.ui.rest;

public abstract class TableClient {

	public TableClient() {}

	public abstract String[][] getData(String serviceName);

	public abstract TableInfo getTableInfo(String serviceName);

}
