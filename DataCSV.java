import java.util.*;
import java.util.function.*;
import java.util.stream.*;

class DataCSV {
	private ArrayList<Predicate<ArrayList<String>>> filterList;
	private ReadCSV csv;
	private int selectedColumn;

	public DataCSV(ReadCSV csv) {
		this.csv = csv;

		filterList = new ArrayList<Predicate<ArrayList<String>>>();
	}

	//Filters the parsed CSV as a Stream and returns another Stream filtered with filters in filterList. (We like filters).
	public Stream<ArrayList<String>> miaouFilter(Stream<ArrayList<String>> s) {
		Stream<ArrayList<String>> t = s;
		for(Predicate<ArrayList<String>> lambda: filterList) {
			t = t.filter(lambda); 
		}
		return t;
	}

	//Add filter to filterList
	public void addFilter(Predicate<ArrayList<String>> filter) {
		filterList.add(filter);
	}

	public void clearFilterList() {
		filterList.clear();
	}

	public String[][] toArray(String columnSort) {
		selectedColumn = findIndexForColumn(columnSort);
		return miaouFilter(csv.getData().stream()) // Generate the Stream and apply filters on it
			.sorted((b,c) -> b.get(selectedColumn).compareTo(c.get(selectedColumn))) // Sort by the 16th column by creating a comparaton
			.limit(10) // Limit to 10 results
			.map(this::extractColumns)
			.toArray(String[][]::new); //Converts the stream into an array 
	}

	public int findIndexForColumn(String name) {
		return csv.findIndexForColumn(name);
	}

	public String[] getColumnsName() {
		return csv.getColumns().toArray(new String[csv.getColumns().size()]);
	}

	public String[] getColumnsName(List<Integer> indexes) {
		ArrayList<String> columns = new ArrayList<String>();
		for (Integer i : indexes) {
			columns.add(csv.getColumns().get(i));
		}
		return columns.toArray(new String[columns.size()]);
	}
		
	public String[] getDisciplines() {
		int i = findIndexForColumn("discipline");
		return csv.getData().stream().filter(b -> !b.get(i).isEmpty()).map(b -> b.get(i)).sorted().distinct().toArray(String[]::new);
	}

	private String[] extractColumns(ArrayList<String> b) {
		return new String[]{b.get(6), b.get(2), b.get(selectedColumn)};
	}
}
