package net.thomasnardone.ui.table;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import net.thomasnardone.ui.DataType;
import net.thomasnardone.ui.EditType;
import net.thomasnardone.ui.FilterType;
import net.thomasnardone.ui.rest.ColumnInfo;
import net.thomasnardone.ui.rest.FilterInfo;

/**
 * Manages conversion of column properties to Java entities.
 * 
 * @author Thomas Nardone
 */
public class ColumnManager {
	public static final String		COLUMNS			= "columns";
	public static final String		DATA_TYPE		= "dataType";
	public static final String		DISPLAY_NAME	= "displayName";
	public static final String		EDIT_TYPE		= "editType";
	public static final String		FILTER			= "filter";
	public static final String		FILTER_ROWS		= FILTER + ".rows";
	public static final String		PREFIX			= "column.";
	public static final String		ROW				= "row";
	public static final String		TYPE			= "type";

	private final List<ColumnInfo>	columns;
	private final List<FilterInfo>	filters;

	/**
	 * Create an empty {@link ColumnManager} and load the properties from <tt>input</tt>.
	 * 
	 * @see Properties#load(InputStream)
	 */
	public ColumnManager(final InputStream input) throws IOException {
		this();
		Properties props = new Properties();
		props.load(input);
		loadProperties(props);
	}

	/**
	 * Create a {@link ColumnManager} from <tt>props</tt>.
	 * 
	 * @param props
	 */
	public ColumnManager(final Properties props) {
		this();
		loadProperties(props);
	}

	/**
	 * Create an empty {@link ColumnManager} and load the properties from <tt>reader</tt>.
	 * 
	 * @see Properties#load(Reader)
	 */
	public ColumnManager(final Reader reader) throws IOException {
		this();
		Properties props = new Properties();
		props.load(reader);
		loadProperties(props);
	}

	private ColumnManager() {
		columns = new ArrayList<>();
		filters = new ArrayList<>();
	}

	public List<ColumnInfo> getColumns() {
		return columns;
	}

	public List<FilterInfo> getFilters() {
		return filters;
	}

	private void loadProperties(final Properties props) {
		String[] columnSplit = props.getProperty(COLUMNS).split(" ");
		for (String column : columnSplit) {
			ColumnInfo info = new ColumnInfo();
			info.setName(column);
			info.setDisplayName(props.getProperty(PREFIX + column + "." + DISPLAY_NAME));
			info.setDataType(DataType.valueOf(props.getProperty(PREFIX + column + "." + DATA_TYPE)));
			info.setEditType(EditType.valueOf(props.getProperty(PREFIX + column + "." + EDIT_TYPE)));
			columns.add(info);
		}

		String filterRowsProp = props.getProperty(FILTER_ROWS);
		if (filterRowsProp != null) {
			int rowCount = Integer.parseInt(filterRowsProp);
			for (int i = 0; i < rowCount; i++) {
				String[] filtersInRow = props.getProperty(FILTER + "." + ROW + i, "").split(" ");
				for (int j = 0; j < filtersInRow.length; j++) {
					String filter = filtersInRow[j];
					FilterInfo info = new FilterInfo();
					info.setColumnName(filter);
					info.setDisplayName(props.getProperty(PREFIX + filter + "." + DISPLAY_NAME, filter));
					info.setRow(i);
					info.setColumn(j);
					final String type = props.getProperty(FILTER + "." + filter + "." + TYPE);
					try {
						info.setType(FilterType.valueOf(type));
					} catch (IllegalArgumentException | NullPointerException e) {
						System.out.println("Invalid filter type for " + filter + ": " + type);
						info.setType(FilterType.Text);
					}
					info.setValues(null); // TODO
					filters.add(info);
				}
			}
		}
	}
}
