import java.util.*;

public class HillClimbing {

    public static Random rand;

    public static final int BoardSize = 8;
    public static final int NumQueens = BoardSize * 9 / 8;
    public static final int maxAllowableResets = 10;

    public static Board currentBoard, refBoard, bestBoard;
    public static int currentCost, lowestCost, thisLowestCost, startingCost;

    //Calculate Heuristic Cost of Given Board
    public static int calculateCost(Board board) {
        //Cost Function: 100 * # Attacking Queens + Total Movement Costs
        return ((board.findNumAttacks() * 100) + board.getAllMoveCost(refBoard));
    }

    public static Board nextBoard(Board board) {
        int boardCost = calculateCost(board);
        ArrayList<ArrayList<QueenEntry>> allMoves = board.allPossibleMoves();

        //Create list of Queen IDs (1-N) and randomize it
        List<Integer> QueenIDs = new ArrayList<Integer>();
        for (int i = 1; i <= NumQueens; i++) {
            QueenIDs.add(i);
        }
        Collections.shuffle(QueenIDs);

        //Iterate through shuffled queen list
        for (int queenID : QueenIDs) {
            ArrayList<QueenEntry> subsetMoves = allMoves.get(queenID-1);

            //Iterate through all possible moves for "queenID-th" queen
            for (QueenEntry i : subsetMoves) {
                Board tmpBoard = board.moveAQueen(queenID, i);
                if(calculateCost(tmpBoard) < boardCost) {
                    return tmpBoard;
                }
                else{
                    //System.out.println("Not as Good");
                }
            }
        }
        //null is returned if no better move was discovered
        return null;
    }

    public static void main(String[] args) {

        //First Choice Rn
        refBoard = Board.ofRandomBoard(8, NumQueens);
        currentBoard = refBoard;
        currentCost = calculateCost(currentBoard);
        startingCost = currentCost;
        System.out.println("Starting Cost " + startingCost);
        lowestCost = currentCost;

        long start = System.nanoTime();

        for(int i =0; i< maxAllowableResets; i++) {

            while (true) {
                if (nextBoard(currentBoard) != null) {
                    //System.out.println(nextBoard(currentBoard));
                    currentBoard = nextBoard(currentBoard);
                } else {
                    thisLowestCost = calculateCost(currentBoard);

                    break;
                }
            }

            if(thisLowestCost < lowestCost){
                lowestCost = thisLowestCost;
                bestBoard = currentBoard;
                System.out.println("New Lowest " + lowestCost);
            }

            if(i < maxAllowableResets){
                currentBoard = refBoard;
            }
        }
        long end = System.nanoTime();
        System.out.println("Final Board Cost: " + lowestCost);
        System.out.println("Elapsed Time in ms: " + ((end-start)/Math.pow(1,6)));

    }
}
