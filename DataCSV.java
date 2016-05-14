import java.util.*;
import java.util.function.*;
import java.util.stream.*;

/**
 * <code>DataCSV</code> class
 * @author Julien Lemaire
 * @author Pierre-Emmanuel Novac
 */
class DataCSV {
	private ArrayList<Predicate<ArrayList<String>>> filterList;
	private ReadCSV csv;
	private int selectedColumn;
	private int order;

	/**
	 * Default constructor of <code>DataCSV</code>
	 * @param csv a ReadCSV object (parsed CSV file)
	 */
	public DataCSV(ReadCSV csv) {
		this.csv = csv;
		this.order = 1;

		filterList = new ArrayList<Predicate<ArrayList<String>>>();
	}

	/**
	 * Apply all the filters in the filterlist to the data stored from <code>CSV</code> file
	 * @param s the stream which we apply the filters on
	 * @return the filtered stream
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
	 * @param filter the filter to add to the list
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
	 * @return the filtered data into a two-dimensions array of String
	 */
	public String[][] toArray(String columnSort, List<Integer> indexes, int numberOfElements) {
		selectedColumn = findIndexForColumn(columnSort);
		return applyFilter(csv.getData().stream()) // Generate the Stream and apply filters on it
			.sorted(this::compare)
			.map((b) -> indexes.stream().map(c -> b.get(c)).toArray(String[]::new)) // Select columns according to indexes
			.limit(numberOfElements) // Limit to 10 results
			.toArray(String[][]::new); //Converts the stream into an array
	}


	/**
	 * Find the index in which the column with the given name is stored
	 * @param name the searched column
	 * @return the index of the searched column
	 */
	public int findIndexForColumn(String name) {
		return csv.findIndexForColumn(name);
	}

	/**
	 * Set the sort order
	 * @param order the new sort order
	 */
	public void setOrder(int order) {
		this.order = order;
	}

	/**
	 * Returns all the <code>CSV</code>'s columns' name
	 * @return the column's name
	 */
	public String[] getColumnsName() {
		return csv.getColumns().toArray(new String[csv.getColumns().size()]);
	}

	/**
	 * Returns all the name of the columns that match the given indexes
	 * @param indexes a list of indexes
	 * @return the name of the columns that match the indexes
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
	 * @param column the column name
	 * @return a String array, containing the values of the column
	 */	
	public String[] getColumnValues(String column) {
		int i = findIndexForColumn(column);
		return csv.getData().stream().filter(b -> !b.get(i).isEmpty()).map(b -> b.get(i)).sorted().distinct().toArray(String[]::new);
	}

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
