package sticmacpiernov.spreadsheet;

import java.util.*;
import java.util.function.*;
import java.util.stream.*;
import sticmacpiernov.spreadsheet.struct.*;

/**
 * This class is used to process data from a ReadCSV object.
 * It can be used to filter, sort and limit lines from a CSV file after
 * reading it with ReadCSV.
 *
 * @author Julien Lemaire
 * @author Pierre-Emmanuel Novac
 */
public class DataCSV {
	private ArrayList<Predicate<ArrayList<String>>> filterList;
	private ReadCSV csv;
	private int selectedColumn;
	private int order;

	/**
	 * Default constructor of <code>DataCSV</code>
	 * @param	csv	a ReadCSV object (parsed CSV file)
	 */
	public DataCSV(ReadCSV csv) {
		this.csv = csv;
		this.order = 1;

		filterList = new ArrayList<Predicate<ArrayList<String>>>();
	}

	/**
	 * Apply all the filters in the filterlist to the data stored from <code>CSV</code> file
	 * @param	s	the stream which we apply the filters on
	 * @return	the filtered stream
	 */
	public Stream<ArrayList<String>> applyFilter(Stream<ArrayList<String>> s) {
		Stream<ArrayList<String>> t = s;
		for(Predicate<ArrayList<String>> lambda: filterList) {
			t = t.filter(lambda);
		}
		return t;
	}

	/**
	 * Add a filter to the filter list
	 * @param	filter	the filter to add to the list
	 */
	public void addFilter(Predicate<ArrayList<String>> filter) {
		filterList.add(filter);
	}

	/**
	 * Clear the filter list
	 */
	public void clearFilterList() {
		filterList.clear();
	}

	/**
	 * Return the filtered data into a two-dimensions array of String
	 * @param	columnSort		the column used to sort results
	 * @param	order			1 for ascending order, -1 for descending order
	 * @param	indexes			indexes of columns to return
	 * @param	numberOfElements	number of elements to return
	 * @return	the filtered data into a two-dimensions array of String
	 */
	public String[][] toArray(String columnSort, int order, List<Integer> indexes, int numberOfElements) {
		this.order = order;
		selectedColumn = findIndexForColumn(columnSort);
		return applyFilter(csv.getData().stream()) // Generate the Stream and apply filters on it
			.sorted(this::compare) // Use our comparator for sorting data
			.map((b) -> indexes.stream().map(c -> b.get(c)).toArray(String[]::new)) // Select columns according to indexes
			.limit(numberOfElements) // Limit returned results
			.toArray(String[][]::new); //Converts the stream into an array
	}

	/**
	 * Execute the requested search.
	 * @param	columnSort	the column used to sort results
	 * @param	order		1 for ascending order, -1 for descending order
	 * @param	selectedColumns	list of columns to return
	 * @param	limit		number of results to return
	 * @param	criteria	list of filters to apply
	 * @return	a ResultSet object containing the table of results and the columns name
	 */
	public ResultSet processSearch(String columnSort, int order, ArrayList<String> selectedColumns, int limit, ArrayList<Filter> criteria) {
		this.clearFilterList();

		for (Filter c: criteria) {
			this.addFilter(b -> b.get(findIndexForColumn(c.getColumn())).equals(c.getValue())); // Add all filter followings the "equals" relationship
		}
		this.addFilter(b -> !(b.get(findIndexForColumn(columnSort)).isEmpty())); // Filter out empty cells in sort column
		this.addFilter(b -> !(b.get(findIndexForColumn(columnSort)).equals("ns"))); // Filter out "ns" cells in sort column
		this.addFilter(b -> !(b.get(findIndexForColumn(columnSort)).equals("nd"))); // Filter out "nd" cells in sort column

		ArrayList<Integer> indexes = new ArrayList<Integer>();

		for(String column: selectedColumns) {
			indexes.add(findIndexForColumn(column));
		}

		if(indexes.size() < 1) return new ResultSet() { // Empty result if no column was selected
			@Override public String[] getColumns() { return new String[0]; }
			@Override public String[][] getContent() { return new String[0][0]; }
		};

		return new ResultSet() {
			@Override public String[] getColumns() { return DataCSV.this.getColumnsName(indexes); }
			@Override public String[][] getContent() { return DataCSV.this.toArray(columnSort, order, indexes, limit); }
		};
	}

	/**
	 * Returns the index of the given column
	 * @param	name	the column name
	 * @return	the index of the given column, -1 when not found
	 */
	public int findIndexForColumn(String name) {
		for (int i = 0 ; i < csv.getColumns().size() ; i++) {
			if (csv.getColumns().get(i).equals(name)) return i;
		}
		return -1;
	}

	/**
	 * Returns all the <code>CSV</code>'s columns' name
	 * @return	all columns name
	 */
	public String[] getColumnsName() {
		return csv.getColumns().toArray(new String[csv.getColumns().size()]);
	}

	/**
	 * Returns all the name of the columns that match the given indexes
	 * @param	indexes a list of column indexes
	 * @return	the name of the columns that match the indexes
	 */
	public String[] getColumnsName(List<Integer> indexes) {
		ArrayList<String> columns = new ArrayList<String>();
		for (Integer i : indexes) {
			columns.add(csv.getColumns().get(i));
		}
		return columns.toArray(new String[columns.size()]);
	}

	/**
	 * Returns the different value of the given column
	 * @param	column	the column name
	 * @return	values from the column
	 */
	public String[] getColumnValues(String column) {
		int i = findIndexForColumn(column);
		if(i < 0) return new String[0]; // empty result if column not found
		return csv.getData().stream().filter(b -> !b.get(i).isEmpty()).map(b -> b.get(i)).sorted().distinct().toArray(String[]::new);
	}

	/**
	 * Implements a Comparator-compatible method used for sorting data.
	 * Tries first to compare the data as Float and if it fails, use String comparison.
	 * Uses the order attribute for reversing the result if needed.
	 * @return 	result of compareTo() on Float or String
	 */
	private int compare(ArrayList<String> b, ArrayList<String> c) {
		String bstr = b.get(selectedColumn);
		String cstr = c.get(selectedColumn);
		try {
			return order*(Float.valueOf(bstr).compareTo(Float.valueOf(cstr)));
		}
		catch (NumberFormatException e) {
			return order*(bstr.compareTo(cstr));
		}
	}
}
