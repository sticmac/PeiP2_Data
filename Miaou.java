import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class Miaou {
	static ArrayList<Predicate<ArrayList<String>>> filterList;

	//Filters the parsed CSV as a Stream and returns another Stream filtered with filters in filterList. (We like filters).
	public static Stream<ArrayList<String>> miaouFilter(Stream<ArrayList<String>> s) {
		Stream<ArrayList<String>> t = s;
		for(Predicate<ArrayList<String>> lambda: filterList) {
			t = runFilter(t, lambda);
		}
		return t;
	}

	//Filter a Stream (with a specified Predicate lambda) and returns it
	public static Stream<ArrayList<String>> runFilter(Stream<ArrayList<String>> s, Predicate<ArrayList<String>> lambda) {
		return s.filter(lambda);
	}

	//Add a filter (as a lambda) in FilterList
	public static void addFilter(Predicate<ArrayList<String>> lambda) {
		filterList.add(lambda);
	}

	public static void main(String[] args) {
		ReadCSV tt = new ReadCSV(); //CSV file is parsed
		tt.printColumns();

		filterList = new ArrayList<Predicate<ArrayList<String>>>();
		addFilter(b -> b.get(2).contains("Toulon")); //add a filter for Toulon universities
		addFilter(b -> !b.get(13).isEmpty()); //add a filter for those which answered the poll (with an answer rate)

		Stream<ArrayList<String>> s = tt.getData().stream(); //Convert the parsed CSV as a stream
//		System.out.println(miaouFilter(s).map);
		//Returns the average answer rate in Toulon
		System.out.println( miaouFilter(s).mapToInt(b -> Integer.parseInt(b.get(12))).average().orElse(0) );



//		ReadCSV tt = new ReadCSV();
//		System.out.println(tt.getData());
//		tt.printColumns();
//		System.out.println( tt.getData().stream().filter(b -> b.get(2).contains("Stringoulon") && !b.get(13).isEmpty() ).mapStringoInt(b -> Integer.parseInt(b.get(12))).average() );

//		miaouFilter(tt.getData().stream());
	}
}
