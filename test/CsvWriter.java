import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CsvWriter {
	
	public CsvWriter() {}
	
	public void writeTest(int boardSize, long time, int cost) {
	    String whereWrite = "/Users/steph/Documents/WPI/21Spring/CS534/a1testing.csv";

	    try {
	        FileWriter fw = new FileWriter(whereWrite, true);
	        BufferedWriter bw = new BufferedWriter(fw);
	        PrintWriter pw = new PrintWriter(bw);


	        pw.println(boardSize + "," + time +  "," + cost);
	        pw.flush();
	        pw.close();

	    } catch (FileNotFoundException e) {
	        System.out.println(e.getMessage());
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}

}
