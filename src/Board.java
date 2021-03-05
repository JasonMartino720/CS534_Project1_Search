import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

	public int[][] board;
	public int boardSize;
	public int score = heuristic();

	public Board(int n) { //generic board constructor

		board = new int[n][n];
		boardSize = n;
	}

	public Board(int[][] b, int n) { //constructor for importing pre-made board

		board = b;
		boardSize = n;
	}

	public int heuristic() {
		return countRowAttack() + countDiagonalRight() + countDiagonalLeft();
	}

	/**
	 * Checks for queens attacking each other on x and y axis
	 * @return # of collisions detected
	 */
	public int countRowAttack() {
		int attackingPairs = 0;
		for (int i = 0; i < boardSize; i++) { //choose row to iterate thru
			int queens = 0;
			for (int j = 0; j < boardSize; j++) { //check each column in row for # of queens
				if (board[i][j] > 0) {
					queens++;
				}
			}
				attackingPairs += ((queens * (queens - 1)) / 2); //Calculate # of collisions detected
		}
		return attackingPairs;
	}

	/**
	 * Counts collision on right diagonal
	 * @return # of collisions detected
	 */
	public int countDiagonalRight() {
		int attackingPairs = 0;

		for (int i = boardSize - 1; i > 0; i--) {
			int queens = 0;
			for (int j = 0, x = i; x <= boardSize - 1; j++, x++) {
				if (board[x][j] > 0) {
					queens++;
				}
			}
			attackingPairs += ((queens * (queens - 1)) / 2);
		}

		for (int i = 0; i <= boardSize - 1; i++) {
			int queens = 0;
			for (int j = 0, y = i; y <= boardSize - 1; j++, y++) {
				if (board[j][y] > 0) {
					queens++;
				}
			}
			attackingPairs += ((queens * (queens - 1)) / 2);
		}

		return attackingPairs;
	}

	/**
	 * Counts collision on Left diagonal
	 * @return # of collisions detected
	 */
	public int countDiagonalLeft() {
		int attackingPairs = 0;
		for (int k = 0; k <= 2 * (boardSize - 1); ++k) {
			int queens = 0;
		    for (int y = 0; y < boardSize; ++y) {
		        int x = k - y;
		        if (x < 0 || x >= boardSize) {
		            // Coordinates are out of bounds; skip.
		        } else {
		            if (board[y][x] > 0) { queens++; }
		        }
		    }
		    attackingPairs += ((queens * (queens - 1)) / 2);
		}

		return attackingPairs;
	}

	/**
	 * Verifies that board should have item in every row to work with
	 * @return whether board is workable
	 */
	public boolean validBoard() {
		for(int[] a : this.board) {
			int sum = 0;
			for(int i : a) {
				sum += i;
			}
			if(sum > 0) return false;
		}
		return true;
	}

//	public Board[] adjBoards() {
//		int dimension = this.boardSize;
//		Board[] ans;
//		for(int[] r : this.board) { //for every row on board
//
//
//		}
//	}

	public static List<List<Integer>> adjRows(int[] row, int length, int weight) {
		List<List<Integer>> ans = new ArrayList<>();

		List<Integer> given = new ArrayList<>();//Step 1: Convert array to arrayList
		for (int num : row) given.add(num);

		int index = 0;
		for (int i = 0; i < row.length; i++) {
			//Step 2: Create List for every type of set possible
			List<Integer> aRow = new ArrayList<>();
			for (int j = 0; j < length; j++) {
				if (j == index) { //Index indicates where value should be added
					aRow.add(weight);
				} else aRow.add(0);
			}
			index++;
			if (!(aRow.equals(given))) ans.add(aRow);
		}
		return ans;
	}
}
