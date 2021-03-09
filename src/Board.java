import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Board {
    //The first 2 of dx and dy are for moving vertically.
    //dSize is used to limit the move mode.
    public static int[] dx = {1,  -1, 0, 0};
    public static int[] dy = {0,  0,  1, -1};

    public enum MoveMode {
        VERTICAL_ONLY, VERTICAL_AND_HORIZONTAL;
    }

    public int dSize = 2; //2 for VERTICAL_ONLY, 4 for VERTICAL_AND_HORIZONTAL,

    public void setMoveMode(MoveMode m) {
        if (m == MoveMode.VERTICAL_ONLY) {
            dSize = 2;
        } else if (m==MoveMode.VERTICAL_AND_HORIZONTAL) {
            dSize = 4;
        }
    }

    public final int size;
    public final int numQueen;

    //The following two are redundant.
    //After you change one of them, use syncWithXXX function to update the other one.
    public int[][] board = null;
    public ArrayList<QueenEntry> queens; // start from 1, end at numQueen, size is numQueen+1, queens[0] is never used.

    //Flat version of board is used for generate random board.
    //The following functions are for the transforms of flat->board, queens->board, board->queens, board->flat.
    //The sync functions are implemented by the transforms.
    public static int[] board2Flat(int[][] board) {
        int n = board.length;
        int[] out  = new int[n*n];
        for(int i = 0; i<n; i++) {
            System.arraycopy(board[i], 0, out, i * n, n);
        }
        return out;
    }

    public static int[][] flat2Board(int[] flat) {
        int n = (int) Math.round(Math.sqrt(flat.length));
        int[][] out  = new int[n][n];
        for (int i=0; i<n; i++) {
            System.arraycopy(flat, i*n, out[i], 0, n);
        }
        return out;
    }

    //weightList index from 0 to numQueen-1.
    public static ArrayList<QueenEntry> board2Queens(int[][] board, int[] weightList) {
        int n = board.length;
        ArrayList<QueenEntry> out = new ArrayList<>();
        out.add(QueenEntry.of(0,0,-1)); //dummy out[0];
        for (int j : weightList) {
            out.add(QueenEntry.of(0, 0, j));
        }
        board2Queens(board, out);
        return out;
    }

    private static void board2Queens(int[][] board, ArrayList<QueenEntry> out) {
        int n = board.length;
        for (int i=0; i<n; i++) {
            for (int j=0; j<n; j++) {
                if (board[i][j] != 0) {
                    out.set(board[i][j], out.get(board[i][j]).setPos(i, j));
                }
            }
        }
    }

    public static int[][] queens2Board(ArrayList<QueenEntry> queens, int size) {
        int[][] out = new int[size][size]; // init to 0 according to java spec.
        for (int i=1; i<=queens.size(); i++) {
            out[queens.get(i).row()][queens.get(i).col()] = i;
        }
        return out;
    }

    public void syncWithBoard() {
        board2Queens(this.board, this.queens);
    }

    public void syncWithQueens() {
        this.board = queens2Board(this.queens, this.size);
    }

    public Board(int[][] inBoard, int[] queenWeights) {
        this.size = inBoard.length;
        this.numQueen = queenWeights.length;
        this.board = BoardUtil.deepcopy(inBoard);
        this.queens = board2Queens(inBoard, queenWeights);
    }

    public Board(int[] inFlatBoard, int[] queenWeights) {
        this.size = (int) Math.round(Math.sqrt(inFlatBoard.length));
        this.numQueen = queenWeights.length;
        this.board = flat2Board(inFlatBoard);
        this.queens = board2Queens(this.board, queenWeights);
    }

    public Board(Board inBoard){
        this.size = inBoard.size;
        this.numQueen = inBoard.numQueen;
        this.board = BoardUtil.deepcopy(inBoard.board);
        this.queens = (ArrayList<QueenEntry>) inBoard.queens.clone();
    }

    public static Board ofBoard(Board inBoard) {
        return new Board(inBoard);
    }

    public static Board ofRandomBoard(int size, int numQueen) {
        int[] queenWeights = BoardUtil.randomWeightList(numQueen);
        int[][] board = new int[size][size];
        Random rng = ThreadLocalRandom.current();
        for (int i=0; i<size; i++) {
            board[rng.nextInt(size)][i] = i+1;
        }
        for (int i=size; i<numQueen; i++) {
            while (true) {
                int ranx = rng.nextInt(size);
                int rany = rng.nextInt(size);
                if (board[ranx][rany] == 0) {
                    board[ranx][rany] = i+1;
                    break;
                }
            }
        }
        return new Board(board, queenWeights);
    }

    private boolean checkCoord(int row, int col) {
        return row>=0 && row<size && col>=0 && col<size;
    }

    //out[i] is the possible moves for ith queen.
    public ArrayList<ArrayList<QueenEntry>> allPossibleMoves() {
        ArrayList<ArrayList<QueenEntry>> out = new ArrayList<>();
        for (int i=1; i<=numQueen; i++) {
            out.add(allPossibleMovesForQueen(i));
        }
        return out;
    }

    public ArrayList<QueenEntry> allPossibleMovesForQueen(int queenIndex) {
        assert queenIndex > 0;
        int currentRow = queens.get(queenIndex).row();
        int currentCol = queens.get(queenIndex).col();
        int weight = queens.get(queenIndex).weight();
        ArrayList<QueenEntry> out = new ArrayList<>();
        for (int d = 0; d < dSize; d++) {
            for (int i = 1; i < size; i++) {
                int moveRow = currentRow + i * dx[d];
                int moveCol = currentCol + i * dy[d];
                if (checkCoord(moveRow, moveCol) && board[currentRow + i * dx[d]][currentCol + i * dy[d]] == 0) {
                    out.add(QueenEntry.of(moveRow, moveCol, weight));
                } else {
                    break;
                }
            }
        }
        return out;
    }

    //don't rely on this, bad performance.
    //weight in newPos is not used.
    public boolean checkValidSingleMove(int queenIndex, QueenEntry newPos) {
        return allPossibleMovesForQueen(queenIndex).stream().anyMatch(n->n.equals(newPos));
    }

    //Single move must be valid move, not checked in this function.
    //weight in newPos is not used.
    public int getSingleMoveCost(int queenIndex, QueenEntry newPos) {
        assert queenIndex>0;
        return getSingleMoveCost(queens.get(queenIndex), newPos);
    }

    //Single move must be valid move, not checked in this function.
    //the weight in 'to' is not used.
    public static int getSingleMoveCost(QueenEntry from, QueenEntry to) {
        int step = Math.max(Math.abs(to.row()- from.row()), Math.abs(to.col() - from.col()));
        return step * from.weight() * from.weight();
    }

    //the weight in 'to' is not used.
    private static int getQueenEntireMoveCost(QueenEntry from, QueenEntry to) {
        int dx = Math.abs(to.row() - from.row());
        int dy = Math.abs(to.col() - from.col());
        int diag = Math.min(dx, dy);
        return diag + dx - diag + dy - diag;
    }

    //The total moving cost from ref board to this.
    public int getAllMoveCost(Board ref) {
        assert ref.numQueen == this.numQueen;
        assert ref.size == this.size;
        int out = 0;
        for (int i = 1; i <= this.numQueen; i++) {
            out += getQueenEntireMoveCost(ref.queens.get(i), this.queens.get(i));
        }
        return out;
    }

    //return the new Board after the move. The weight in newPos is not used.
    public Board moveAQueen(int queenIndex, QueenEntry newPos) {
        assert queenIndex>0;
        Board out = Board.ofBoard(this);
        QueenEntry n = newPos.setWeight(out.queens.get(queenIndex).weight());
        out.board[out.queens.get(queenIndex).row()][out.queens.get(queenIndex).col()] = 0;
        out.queens.set(queenIndex, n);
        out.board[n.row()][n.col()] = queenIndex;
        return out;
    }

    public int findNumAttacks() {
        int[] rowCount = new int[size];
        int[] colCount = new int[size];
        int[] mainDiagCount = new int[size * 2];
        int[] antiDiagCount = new int[size * 2];
        for (QueenEntry q: queens) {
            if (q.equals(QueenEntry.of(0,0,-1))) continue; // for dummy queens[0].
            rowCount[q.row()] ++;
            colCount[q.col()] ++;
            mainDiagCount[q.col()-q.row()+size]++;
            antiDiagCount[q.col()+q.row()]++;
        }

        int out = 0;
        for (int c:rowCount) {
            if (c!=0) {
                out += c*(c-1)/2;
            }
        }

        for (int c:colCount) {
            if (c!=0) {
                out += c*(c-1)/2;
            }
        }

        for (int c:mainDiagCount) {
            if (c!=0) {
                out += c*(c-1)/2;
            }
        }

        for (int c:antiDiagCount) {
            if (c!=0) {
                out += c*(c-1)/2;
            }
        }

        return out;
    }

    @Override
    public String toString() {
        return "Board{" +
                "board=\n" + Arrays.stream(board).map(i->Arrays.toString(i)).collect(Collectors.joining("\n")) +
                "\nqueens=" + queens.stream().map(i->i.toString()).collect(Collectors.joining("\n")) +
                '}';
    }

    public static void main(String[] args) {
        int[][] startboard = {
                {1,0,0,0,0,0,0,0},
                {0,2,0,0,0,0,0,0},
                {0,0,3,0,0,0,0,0},
                {0,0,0,4,0,0,0,0},
                {0,0,0,0,5,0,0,0},
                {0,0,0,0,0,6,0,0},
                {0,0,0,0,0,0,7,0},
                {0,0,9,0,0,0,0,8}
        };
        Board b = new Board(startboard, new int[9]);
        System.out.println(b.findNumAttacks());
        Board rb = Board.ofRandomBoard(4, 4);
        System.out.println(rb);
        ArrayList<QueenEntry> moves = rb.allPossibleMovesForQueen(1);
        System.out.println(moves.toString());
        Board newb = rb.moveAQueen(1, moves.get(0));
        System.out.println(newb);
        
 //       Board b2 = Board.ofRandomBoard(5, 5);
 //       b2.printBoard();
    }
    
    public void printBoard() {
    	for (int[] i : board) {
    		System.out.println(Arrays.toString(i));
    	}System.out.println();
    }

    
    public List<Board> adjBoards() {
		int bSize = board.length;
		List<Board> ans = new ArrayList<>();
		for (int i = 1; i < bSize +1; i++) {
			ArrayList<QueenEntry> moves = this.allPossibleMovesForQueen(i);
			for (QueenEntry qe : moves) {
				ans.add(moveAQueen(i, qe));
			}		
		}
		return ans;
    }
}

	



