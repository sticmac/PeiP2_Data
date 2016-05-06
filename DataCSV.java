
class DataCSV {
	private ArrayList<Predicate<ArrayList<String>>> filterList;

	public DataCSV(ReadCSV csv) {
		filterList = new ArrayList<Predicate<ArrayList<String>>>();

		
		filterList.add(b -> b.get(2).contains("Toulon")); //add a filter for Toulon universities
		filterList.add(b -> !b.get(13).isEmpty()); //add a filter for those which answered the poll (with an answer rate)
		

		Stream<ArrayList<String>> s = tt.getData().stream(); //Convert the parsed CSV as a stream
		//Returns the average answer rate in Toulon
		System.out.println( miaouFilter(s).mapToInt(b -> Integer.parseInt(b.get(12))).average().orElse(0) );
	}

	//Filters the parsed CSV as a Stream and returns another Stream filtered with filters in filterList. (We like filters).
	public static Stream<ArrayList<String>> miaouFilter(Stream<ArrayList<String>> s) {
		Stream<ArrayList<String>> t = s;
		for(Predicate<ArrayList<String>> lambda: filterList) {
			t = t.filter(lambda); 
		}
		return t;
	}
}
