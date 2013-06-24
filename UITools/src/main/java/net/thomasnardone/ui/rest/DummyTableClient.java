package net.thomasnardone.ui.rest;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import net.thomasnardone.ui.DataType;
import net.thomasnardone.ui.table.ColumnManager;

public class DummyTableClient extends TableClient {

	public DummyTableClient() {}

	@Override
	public String[][] getData(final Set<String> queryParams) {
		return new String[][] { { "BlewLabel Perennials", "Achillea", "Apricot Delight", "#1", "Centerton", "800", "32" },
				{ "BlewLabel Perennials", "Achillea", "Apricot Delight", "#2", "Centerton", "1600", "32" },
				{ "BlewLabel Perennials", "Achillea", "Peachy Seduction", "#1", "Aris", "800", "32" },
				{ "BlewLabel Perennials", "Achillea", "Peachy Seduction", "#2", "Aris", "1500", "32" },
				{ "BlewLabel Perennials", "Achillea", "Pomegranate", "#1", "Centerton", "800", "32" },
				{ "BlewLabel Perennials", "Achillea", "Pomegranate", "#2", "Centerton", "1700", "32" },
				{ "BlewLabel Perennials", "Achillea", "Strawberry Seduction", "#1", "Centerton", "1100", "32" },
				{ "BlewLabel Perennials", "Achillea", "Strawberry Seduction", "#2", "Centerton", "2000", "32" },
				{ "BlewLabel Perennials", "Achillea", "Sunny Seduction", "#1", "Centerton", "600", "32" },
				{ "BlewLabel Perennials", "Achillea", "Sunny Seduction", "#2", "Centerton", "1600", "32" },
				{ "BlewLabel Perennials", "Achillea", "Wonderful Wampee", "#1", "Centerton", "700", "32" },
				{ "BlewLabel Perennials", "Achillea", "Wonderful Wampee", "#2", "Centerton", "1000", "32" },
				{ "BlewLabel Perennials", "Aegopodium", "Snow on the Mountain", "#2", "Centerton", "1400", "32" },
				{ "BlewLabel Perennials", "Agastache", "Blue Fortune", "#2", "Centerton", "1600", "32" },
				{ "BlewLabel Perennials", "Agastache", "Golden Jubilee", "#2", "KubePak", "600", "32" },
				{ "BlewLabel Perennials", "Alchemilla", "Lady's Mantle", "#2", "Aris", "1800", "32" },
				{ "BlewLabel Perennials", "Amsonia", "Blue Ice", "#1", "North Creek", "1100", "18" },
				{ "BlewLabel Perennials", "Amsonia", "hubrictii", "#1", "North Creek", "1200", "18" },
				{ "BlewLabel Perennials", "Anemone", "Pretty Lady Diana", "#2", "Pioneer", "800", "17" },
				{ "BlewLabel Perennials", "Aquilegia", "Cameo Blue & White", "#1", "KubePak", "1100", "34" },
				{ "BlewLabel Perennials", "Aquilegia", "Origami Blue & White", "#2", "KubePak", "1200", "34" },
				{ "BlewLabel Perennials", "Aquilegia", "Origami Rose & White", "#2", "KubePak", "1100", "34" },
				{ "BlewLabel Perennials", "Aquilegia", "Winky Double Blue & White", "#2", "KubePak", "1300", "34" },
				{ "BlewLabel Perennials", "Aquilegia", "Winky Double Red & White", "#2", "KubePak", "1500", "34" },
				{ "BlewLabel Perennials", "Arenaria", "Lemon Ice", "#1", "Ball Dar.", "1200", "30" },
				{ "BlewLabel Perennials", "Arenaria", "montana", "#1", "KubePak", "1200", "30" },
				{ "BlewLabel Perennials", "Armeria", "Armada Rose", "#1", "KubePak", "900", "33" },
				{ "BlewLabel Perennials", "Artemisia", "Silvermound", "#1", "Walters", "1200", "10" },
				{ "BlewLabel Perennials", "Artemisia", "Silvermound", "#2", "Walters", "3000", "10" },
				{ "BlewLabel Perennials", "Asclepias", "tuberosa", "#1", "North Creek", "2500", "32" },
				{ "BlewLabel Perennials", "Aster", "Alert", "#2", "Centerton", "800", "19" },
				{ "BlewLabel Perennials", "Aster", "Alert", "#1", "Centerton", "500", "19" },
				{ "BlewLabel Perennials", "Aster", "Blue Autumn", "#2", "Pioneer", "800", "34" },
				{ "BlewLabel Perennials", "Aster", "Professor Kippenberg", "#1", "Centerton", "500", "19" },
				{ "BlewLabel Perennials", "Aster", "Professor Kippenberg", "#2", "Centerton", "800", "19" },
				{ "BlewLabel Perennials", "Aster", "Purple Dome", "#2", "Centerton", "1500", "19" },
				{ "BlewLabel Perennials", "Aster", "Wood's Light Blue", "#2", "North Creek", "800", "19" },
				{ "BlewLabel Perennials", "Aster", "Wood's Pink", "#2", "North Creek", "500", "19" },
				{ "BlewLabel Perennials", "Astilbe", "Delft Lace", "#2", "Pioneer", "3000", "32" },
				{ "BlewLabel Perennials", "Astilbe", "Fanal", "#1", "Pioneer", "1000", "32" },
				{ "BlewLabel Perennials", "Astilbe", "Fanal", "#2", "Pioneer", "1500", "32" },
				{ "BlewLabel Perennials", "Astilbe", "Hennie Graafland", "#1", "Pioneer", "600", "32" },
				{ "BlewLabel Perennials", "Astilbe", "Hennie Graafland", "#2", "Pioneer", "1100", "32" },
				{ "BlewLabel Perennials", "Astilbe", "Montgomery", "#2", "Pioneer", "1300", "32" },
				{ "BlewLabel Perennials", "Astilbe", "Peach Blossom", "#1", "Pioneer", "1100", "32" },
				{ "BlewLabel Perennials", "Astilbe", "Peach Blossom", "#2", "Pioneer", "1500", "32" },
				{ "BlewLabel Perennials", "Astilbe", "Pink Lightning", "#2", "Pioneer", "1300", "32" },
				{ "BlewLabel Perennials", "Astilbe", "Short 'n Sweet Fireberry", "#1", "Walters", "2500", "32" },
				{ "BlewLabel Perennials", "Astilbe", "Short 'n Sweet Sugarberry", "#1", "Walters", "1200", "32" },
				{ "BlewLabel Perennials", "Astilbe", "Visions", "#1", "Pioneer", "800", "32" },
				{ "BlewLabel Perennials", "Astilbe", "Visions", "#2", "Pioneer", "3400", "32" },
				{ "BlewLabel Perennials", "Astilbe", "Visions in Pink", "#2", "Pioneer", "2800", "32" },
				{ "BlewLabel Perennials", "Astilbe", "Visions in Red", "#2", "Pioneer", "3200", "32" },
				{ "BlewLabel Perennials", "Astilbe", "Visions in White", "#1", "Pioneer", "700", "32" },
				{ "BlewLabel Perennials", "Astilbe", "Visions in White", "#2", "Pioneer", "1600", "32" },
				{ "BlewLabel Perennials", "Astilbe", "Younique Cerise", "#1", "Pioneer", "1400", "32" },
				{ "BlewLabel Perennials", "Astilbe", "Younique Silvery Pink", "#1", "Pioneer", "1000", "32" },
				{ "BlewLabel Perennials", "Astilbe", "Younique White", "#1", "Pioneer", "1300", "32" },
				{ "BlewLabel Perennials", "Aurbrieta", "Audrey Blue", "#1", "KubePak", "1000", "38" },
				{ "BlewLabel Perennials", "Baptisa", "Solar Flare", "#2", "Walters", "800", "13" },
				{ "BlewLabel Perennials", "Baptisa", "Starlight", "#2", "North Creek", "600", "15" },
				{ "BlewLabel Perennials", "Baptisa", "Twilight", "#2", "Walters", "800", "13" },
				{ "BlewLabel Perennials", "Campanula", "Blue Clips", "#1", "KubePak", "1000", "32" },
				{ "BlewLabel Perennials", "Campanula", "Blue Waterfall", "#1", "Pioneer", "1200", "34" },
				{ "BlewLabel Perennials", "Campanula", "Blue Waterfall", "#2", "Pioneer", "1600", "34" },
				{ "BlewLabel Perennials", "Campanula", "Viking", "#2", "Pioneer", "800", "34" },
				{ "BlewLabel Perennials", "Ceratostigma", "plumbago", "#1", "Centerton", "2000", "25" },
				{ "BlewLabel Perennials", "Chelone", "Hot Lips", "#2", "Centerton", "1400", "34" },
				{ "BlewLabel Perennials", "Coreopsis", "Crème Brulee", "#2", "Pioneer", "1000", "33" },
				{ "BlewLabel Perennials", "Coreopsis", "Early Sunrise", "#1", "KubePak", "2000", "33" },
				{ "BlewLabel Perennials", "Coreopsis", "Early Sunrise", "#2", "KubePak", "3000", "33" },
				{ "BlewLabel Perennials", "Coreopsis", "Dahl Baby", "#1", "Aris", "1500", "33" },
				{ "BlewLabel Perennials", "Coreopsis", "Galaxy", "#1", "Pioneer", "1000", "33" },
				{ "BlewLabel Perennials", "Coreopsis", "Jethro Tull", "#1", "North Creek", "1200", "33" },
				{ "BlewLabel Perennials", "Coreopsis", "Jethro Tull", "#2", "North Creek", "2500", "33" },
				{ "BlewLabel Perennials", "Coreopsis", "Little Sundial", "#1", "Walters", "1500", "33" },
				{ "BlewLabel Perennials", "Coreopsis", "Mercury Rising", "#2", "Pioneer", "2500", "33" },
				{ "BlewLabel Perennials", "Coreopsis", "Moonbeam", "#1", "Centerton", "1500", "33" },
				{ "BlewLabel Perennials", "Coreopsis", "Moonbeam", "#2", "Centerton", "4000", "33" },
				{ "BlewLabel Perennials", "Coreopsis", "rosea", "#1", "Centerton", "1000", "33" },
				{ "BlewLabel Perennials", "Coreopsis", "rosea", "#2", "Centerton", "1500", "33" },
				{ "BlewLabel Perennials", "Coreopsis", "Sunfire", "#1", "KubePak", "1000", "33" },
				{ "BlewLabel Perennials", "Coreopsis", "Sunfire", "#2", "KubePak", "2000", "33" },
				{ "BlewLabel Perennials", "Coreopsis", "Sweet Dreams", "#2", "North Creek", "1000", "33" },
				{ "BlewLabel Perennials", "Coreopsis", "Zagreb", "#1", "Centerton", "3000", "33" },
				{ "BlewLabel Perennials", "Coreopsis", "Zagreb", "#2", "Centerton", "5500", "33" },
				{ "BlewLabel Perennials", "Crocosmia", "Lucifer", "#2", "Ednie", "1500", "10" },
				{ "BlewLabel Perennials", "Delosperma", "Firespinner", "#2", "Aris", "1500", "34" },
				{ "BlewLabel Perennials", "Delosperma", "John Proffitt", "#2", "Centerton", "1000", "34" },
				{ "BlewLabel Perennials", "Delosperma", "Lemon Ice™", "#1", "Centerton", "1000", "34" },
				{ "BlewLabel Perennials", "Delosperma", "Lemon Ice™", "#2", "Centerton", "1500", "34" },
				{ "BlewLabel Perennials", "Delosperma", "Mesa Verde", "#1", "Centerton", "1000", "34" },
				{ "BlewLabel Perennials", "Delosperma", "Mesa Verde", "#2", "Centerton", "1500", "34" },
				{ "BlewLabel Perennials", "Delosperma", "Raspberry Ice™", "#2", "Centerton", "1000", "34" },
				{ "BlewLabel Perennials", "Delphinium", "Magic Fountains Dark Blue", "#2", "Seed", "1200", "17" },
				{ "BlewLabel Perennials", "Delphinium", "Magic Fountains Sky Blue", "#2", "Seed", "1000", "17" },
				{ "BlewLabel Perennials", "Dianthus", "Eastern Star", "#1", "Pioneer", "1000", "31" },
				{ "BlewLabel Perennials", "Dianthus", "Eastern Star", "#2", "Pioneer", "2000", "31" },
				{ "BlewLabel Perennials", "Dianthus", "Everlast Orchid", "#1", "Ball Dar.", "1000", "31" },
				{ "BlewLabel Perennials", "Dianthus", "Everlast White Eye", "#1", "Ball Dar.", "1000", "31" } };
	}

	@Override
	public TableInfo getTableInfo(final String tableName) {
		List<FormatInfo> formats = new ArrayList<>();

		formats.add(formatInfo(DataType.Integer, "#,##0"));

		Properties props = new Properties();
		try {
			props.load(new FileInputStream("src/main/config/request_table.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ColumnManager mgr = new ColumnManager(props);

		List<ColumnInfo> columns = mgr.getColumns();
		for (ColumnInfo column : columns) {
			if ("source".equals(column.getName())) {
				column.setValues(Arrays.asList("Aris", "Ball Dar.", "Centerton", "Ednie", "KubePack", "North Creek", "Pioneer",
						"Seed", "Walters"));
			} else if ("rWeek".equals(column.getName())) {
				List<String> weeks = new ArrayList<>();
				for (int i = 1; i < 53; i++) {
					weeks.add(Integer.toString(i));
				}
				column.setValues(weeks);
			}
		}
		List<FilterInfo> filters = mgr.getFilters();
		for (FilterInfo filter : filters) {
			if ("size".equals(filter.getColumnName())) {
				filter.setValues(Arrays.asList("#1", "#2"));
			}
		}

		TableInfo info = new TableInfo();
		info.setColumns(columns);
		info.setFormats(formats);
		info.setFilters(filters);
		return info;
	}

	private FormatInfo formatInfo(final DataType dataType, final String format) {
		FormatInfo info = new FormatInfo();
		info.setDataType(dataType);
		info.setFormat(format);
		return info;
	}
}
