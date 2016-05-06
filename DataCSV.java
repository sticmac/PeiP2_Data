import java.util.*;
import java.util.function.*;
import java.util.stream.*;

class DataCSV {
	private ArrayList<Predicate<ArrayList<String>>> filterList;
	private ReadCSV csv;

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

	public String[][] toArray() {
		return miaouFilter(csv.getData().stream()) // Generate the Stream and apply filters on it
			.sorted((b,c) -> b.get(15).compareTo(c.get(15))) // Sort by the 16th column by creating a comparaton
			.limit(10) // Limit to 10 results
			.map(DataCSV::extractColumns)
			.toArray(String[][]::new); //Converts the stream into an array 
	}

	public String[] getColumnsName() {
		return extractColumns(csv.getColumns());
	}

	public String[] getDisciplines() {
		int i = findIndexForColumn("discipline");
		return csv.getData().stream().filter(b -> !b.get(i).map(b -> return b.get(i)).sorted().distinct().toArray(String[]::new);
	}

	private static String[] extractColumns(ArrayList<String> b) {
		return new String[]{b.get(2), b.get(15)};
	}
}
