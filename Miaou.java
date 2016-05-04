import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class Miaou {
	static ArrayList<Predicate<ArrayList<String>>> filterList;


	public static Stream<ArrayList<String>> miaouFilter(Stream<ArrayList<String>> s) {
		Stream<ArrayList<String>> t = s;
		for(Predicate<ArrayList<String>> lambda: filterList) {
			t = runFilter(t, lambda);
		}
		return t;
	}

	public static Stream<ArrayList<String>> runFilter(Stream<ArrayList<String>> s, Predicate<ArrayList<String>> lambda) {
		return s.filter(lambda);
	}

	public static void addFilter(Predicate<ArrayList<String>> lambda) {
		filterList.add(lambda);
	}

	public static void main(String[] args) {
		ReadCSV tt = new ReadCSV();
		tt.printColumns();

		filterList = new ArrayList<Predicate<ArrayList<String>>>();
		addFilter(b -> b.get(2).contains("Toulon"));
		addFilter(b -> !b.get(13).isEmpty());

		Stream<ArrayList<String>> s = tt.getData().stream();
//		System.out.println(miaouFilter(s).map);
		System.out.println( miaouFilter(s).mapToInt(b -> Integer.parseInt(b.get(12))).average().orElse(0) );

		filterList.remove(filterList.size()-1);
		addFilter(b -> !b.get(15).isEmpty());

		miaouFilter(tt.getData().stream()) // Generate the Stream and apply filters on it
			.sorted((b,c) -> b.get(15).compareTo(c.get(15))) // Sort by the 16th column by creating a comparaton
			.limit(10) // Limit to 10 results
			.map(b -> { // Generate another stream with ArrayLists
				ArrayList<String> c = new ArrayList<String>();
				c.add(b.get(2)); // keeping only 3rd
				c.add(b.get(15)); // and 16th columns
				return c; })
			.forEach(System.out::println); // Then print all of those new ArrayLists



//		ReadCSV tt = new ReadCSV();
//		System.out.println(tt.getData());
//		tt.printColumns();
//		System.out.println( tt.getData().stream().filter(b -> b.get(2).contains("Stringoulon") && !b.get(13).isEmpty() ).mapStringoInt(b -> Integer.parseInt(b.get(12))).average() );

//		miaouFilter(tt.getData().stream());
	}
}
