
public class Board {

	public int[][] board;
	public int boardSize;
	public int attackingQueens;

	public Board(int n) {

		board = new int[n][n];
		boardSize = n;
	}

	public int countAttacking() {
		return countRowAttack() + countDiagonalRight() + countDiagonalLeft();
	}

	public int countRowAttack() {
		int attackingPairs = 0;
		for (int i = 0; i < boardSize; i++) {
			int queens = 0;
			for (int j = 0; j < boardSize; j++) {
				if (board[i][j] > 0) {
					queens++;
				}
			}
				attackingPairs += ((queens * (queens - 1)) / 2);
		}
		return attackingPairs;
	}

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

//	public int totalCost() {
//
//	}
//
//	public int moveCost() {
//
//	}

}
