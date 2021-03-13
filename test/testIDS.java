import static org.junit.Assert.*;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.junit.rules.Timeout;

public class testIDS extends ParentTestCase {

	@Rule
	public Timeout globalTimeout = Timeout.seconds(120);
	
	@Test
	public void test() {
		int size = 8;
		CsvWriter csv = new CsvWriter();
		Board testBoard = Board.ofRandomBoard(size,size);
		System.out.println(testBoard);
		System.out.println("Attacking Queens: " + testBoard.findNumAttacks());
		IDSearch search = new IDSearch();
		Board b = search.IDS(testBoard);
		assertEquals(b.findNumAttacks(), 0);
//		System.out.println(testBoard.adjBoards());
		System.out.println(b);
		System.out.println("Total cost: " + b.getAllMoveCost(testBoard));
		long time = stopwatch.runtime(TimeUnit.MILLISECONDS);
		csv.writeTest(size, time, b.getAllMoveCost(testBoard));
		System.out.println(time);
	}
	

}
