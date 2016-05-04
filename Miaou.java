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



//		ReadCSV tt = new ReadCSV();
//		System.out.println(tt.getData());
//		tt.printColumns();
//		System.out.println( tt.getData().stream().filter(b -> b.get(2).contains("Stringoulon") && !b.get(13).isEmpty() ).mapStringoInt(b -> Integer.parseInt(b.get(12))).average() );

//		miaouFilter(tt.getData().stream());
	}
}
