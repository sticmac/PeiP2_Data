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


	public String[][] toArray(String columnSort, List<Integer> indexes) {
		selectedColumn = findIndexForColumn(columnSort);
		return miaouFilter(csv.getData().stream()) // Generate the Stream and apply filters on it
			.sorted(this::compare)
			.limit(10) // Limit to 10 results
//			.map(this.extractColumns(indexes))
			.map((b) -> indexes.stream().map(c -> b.get(c)).toArray(String[]::new))
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

	private int compare(ArrayList<String> b, ArrayList<String> c) {
		String bstr = b.get(selectedColumn);
		String cstr = c.get(selectedColumn);
		try {
			return Float.valueOf(bstr).compareTo(Float.valueOf(cstr));
		}
		catch (NumberFormatException e) {
			return bstr.compareTo(cstr);
		}
	}

	private <E extends String, T extends List<E>> Function<T, String[]> extractColumns(List<Integer> indexes) {
/*		return new Function<T, String[]>(){
			@Override
			public String[] apply(T b) {
				return new String[]{b.get(6), b.get(2), b.get(selectedColumn)};
			}
		};*/
//		a = new String[indexes.length];
//		for(int i = 0; i < indexes.length; i++) a[i] = b.get(i);
		return (b) -> indexes.stream().map(c -> b.get(c)).toArray(String[]::new);
/*		return (b) -> {
			String[] a = new String[indexes.length];
			for(int i = 0; i < indexes.length; i++) a[i] = b.get(i);
			return a;
		};*/
//		return (b) -> new String[]{b.get(6), b.get(2), b.get(selectedColumn)};
	}
}
