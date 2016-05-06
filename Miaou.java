import java.util.*;
import java.util.function.*;
import java.util.stream.*;

public class Miaou {

	


	public static void main(String[] args) {
		ReadCSV tt = new ReadCSV(); //CSV file is parsed
		tt.printColumns();


		
//		ReadCSV tt = new ReadCSV();
//		System.out.println(tt.getData());
//		tt.printColumns();
//		System.out.println( tt.getData().stream().filter(b -> b.get(2).contains("Stringoulon") && !b.get(13).isEmpty() ).mapStringoInt(b -> Integer.parseInt(b.get(12))).average() );

//		miaouFilter(tt.getData().stream());
	}
}
