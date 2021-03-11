import static org.junit.Assert.*;

import org.junit.Test;

public class testBoard {

	@Test
	public void test4By4() {
		Board testBoard = new Board(ExampleBoards.exampleBoardOne, 4);
		assertEquals(testBoard.countRowAttack(), 0);
		assertEquals(testBoard.countDiagonalLeft(), 0);
		assertEquals(testBoard.countDiagonalRight(), 6);
		assertEquals(testBoard.heuristic(), 6);
	}

	@Test
	public void test8By8() {
		Board testBoard = new Board(ExampleBoards.exampleBoardThree, 8);
		assertEquals(testBoard.countRowAttack(), 0);
		assertEquals(testBoard.countDiagonalLeft(), 0);
		assertEquals(testBoard.countDiagonalRight(), 28);
	}

	@Test
	public void test2() {
		Board testBoard = new Board(5);
		testBoard.board[0][4] = 1;
		testBoard.board[1][0] = 1;
		testBoard.board[4][0] = 1;
		assertEquals(testBoard.countRowAttack(), 0);
		assertEquals(testBoard.countDiagonalLeft(), 1);
		assertEquals(testBoard.countDiagonalRight(), 0);
	}


	@Test
	public void test3() {
		Board testBoard = new Board(5);
		testBoard.board[3][1] = 1;
		testBoard.board[3][0] = 1;
		assertEquals(testBoard.countRowAttack(), 1);
		assertEquals(testBoard.countDiagonalLeft(), 0);
		assertEquals(testBoard.countDiagonalRight(), 0);
	}

	@Test
	public void test4() {
		Board testBoard = new Board(5);
		testBoard.board[2][4] = 1;
		testBoard.board[2][1] = 1;
		testBoard.board[2][0] = 1;
		testBoard.board[2][2] = 1;
		testBoard.board[2][3] = 1;
		testBoard.board[2][2] = 10;
		assertEquals(testBoard.heuristic(), 10);
		testBoard.board[2][1] = 0;
		assertEquals(testBoard.countRowAttack(),6);
	}

	@Test
	public void testDiagRight() {
		Board testBoard = new Board(5);
		testBoard.board[0][0] = 1;
		testBoard.board[1][1] = 1;
		testBoard.board[2][2] = 1;
		testBoard.board[3][3] = 1;
		testBoard.board[4][4] = 1;
		testBoard.board[2][2] = 10;
		assertEquals(testBoard.countDiagonalRight(), 10);
		assertEquals(testBoard.countDiagonalLeft(), 0);
		assertEquals(testBoard.countRowAttack(), 0);
	}

	@Test
	public void testDiagRight2() {
		Board testBoard = new Board(5);
		testBoard.board[1][0] = 1;
		testBoard.board[2][1] = 1;
		testBoard.board[3][2] = 1;
		testBoard.board[4][3] = 1;
		testBoard.board[0][0] = 1;
		testBoard.board[4][4] = 10;
		assertEquals(testBoard.countDiagonalRight(), 7);
	}

	@Test
	public void testDiagRight3() {
		Board testBoard = new Board(8);
		testBoard.board[0][0] = 1;
		testBoard.board[1][1] = 1;
		testBoard.board[2][2] = 1;
		testBoard.board[3][3] = 1;
		testBoard.board[5][5] = 1;
		testBoard.board[4][4] = 10;
		testBoard.board[7][7] = 1;
		testBoard.board[6][6] = 1;
		testBoard.board[3][2] = 1;
		testBoard.board[4][3] = 1;
		assertEquals(testBoard.countDiagonalRight(), 29);
		assertEquals(testBoard.countDiagonalLeft(), 0);
	}

	@Test
	public void testDiagRight4() {
		Board testBoard = new Board(8);
		testBoard.board[1][5] = 1;
		testBoard.board[1][1] = 1;
		testBoard.board[2][2] = 1;
		testBoard.board[3][3] = 1;
		testBoard.board[2][6] = 1;
		testBoard.board[4][4] = 10;
		testBoard.board[7][7] = 1;
		testBoard.board[6][6] = 1;
		testBoard.board[3][2] = 1;
		testBoard.board[4][3] = 1;
		assertEquals(testBoard.countDiagonalRight(), 17);
		assertEquals(testBoard.countDiagonalLeft(), 2);
	}

	@Test
	public void testDiagLeft() {
		Board testBoard = new Board(8);
		testBoard.board[2][5] = 1;
		testBoard.board[6][1] = 1;
		testBoard.board[2][2] = 1;
		testBoard.board[3][4] = 1;
		testBoard.board[2][6] = 1;
		testBoard.board[5][2] = 10;
		testBoard.board[0][7] = 1;
		testBoard.board[1][6] = 1;
		testBoard.board[3][2] = 1;
		testBoard.board[4][3] = 1;
		testBoard.board[7][0] = 1;
		assertEquals(testBoard.countDiagonalRight(), 1);
		assertEquals(testBoard.countDiagonalLeft(), 28);
		assertEquals(testBoard.countRowAttack(), 4);
	}
}
