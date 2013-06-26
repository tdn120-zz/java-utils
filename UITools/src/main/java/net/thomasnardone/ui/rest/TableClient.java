package net.thomasnardone.ui.rest;

import java.util.List;

public abstract class TableClient {

	public TableClient() {}

	public abstract String[][] getData(String serviceName);

	public abstract TableInfo getTableInfo(String serviceName);

	public abstract boolean updateTable(String serviceName, List<UpdateInfo> update);
}
