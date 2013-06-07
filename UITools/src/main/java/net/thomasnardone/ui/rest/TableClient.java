package net.thomasnardone.ui.rest;

import java.util.Set;

public abstract class TableClient {

	public TableClient() {}

	public abstract String[][] getData(Set<String> queryParams);

	public abstract TableInfo getTableInfo(String tableName);

}
