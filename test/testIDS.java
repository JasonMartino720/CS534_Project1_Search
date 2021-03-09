import static org.junit.Assert.*;

import org.junit.Test;

public class testIDS {

	@Test
	public void test() {
		Board testBoard = Board.ofRandomBoard(4, 4);
		System.out.println(testBoard);
		System.out.println("Attacking Queens: " + testBoard.findNumAttacks());
		IDSearch search = new IDSearch();
		Board b = search.IDS(testBoard);
		assertEquals(b.findNumAttacks(), 0);
//		System.out.println(testBoard.adjBoards());
		System.out.println(b);
	}

}
