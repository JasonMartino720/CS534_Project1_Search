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

	/**
	 *
	 * @param thisBoard board whose adjacencies we will find
	 * @return List of all boards one move away from current state
	 */
	public static List<Board> adjBoards(int[][] thisBoard) {
		int bSize = thisBoard.length;
		List<Board> ans = new ArrayList<>();

		for(int colIndex = 0; colIndex < bSize; colIndex++) { //for every column on board
			List<Integer> aCol = new ArrayList<>();
			for(int r = 0; r < bSize; r++) aCol.add(thisBoard[r][colIndex]); //generate list of each item in column colIndex, populate it

			for(List<Integer> newCol : adjCols(aCol,1)) {// for every other column possible off this one
				int[][] boardCopy = Arrays.stream(thisBoard).map(int[]::clone).toArray(int[][]::new); //use streaming to make clone of original board
				//int[] newRowArray = newCol.stream().mapToInt(i->i).toArray(); //use streaming to make List<Integer> into int[]

				for(int j = 0; j < bSize; j++) boardCopy[j][colIndex] = newCol.get(j); //for each value in the specified column (colIndex), set to new row values

				Board bObject = new Board(boardCopy, bSize);
				ans.add(bObject);
			}
		}
		return ans;
	}

	public static List<List<Integer>> adjCols(List<Integer> aCol, int weight) {
		int bSize = aCol.size();
		List<List<Integer>> ans = new ArrayList<>();

		int index = 0;
		for (int i = 0; i < bSize; i++) { //Step 2: Create List for every type of set possible
			List<Integer> uniqueCol = new ArrayList<>();
			for (int j = 0; j < bSize; j++) {
				if (j == index) uniqueCol.add(weight);//Index indicates where value should be added
				else uniqueCol.add(0);
			}
			index++;
			if (!(uniqueCol.equals(aCol))) ans.add(uniqueCol);
		}
		return ans;
	}
}
