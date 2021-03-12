import java.util.*;

public class HillClimbing {


    public static final int BoardSize = 8;
    public static final int NumQueens = BoardSize;

    public static Board currentBoard, refBoard, bestBoard;
    public static int currentCost, lowestCost, thisLowestCost, startingCost;

    public static Set<Board> reachableBoards = new HashSet<>();
    public static HashMap<Board, Integer> priorityQ = new HashMap<>();
    public static HashMap<Board, Integer> costToReach = new HashMap<>();
    public static HashMap<Board, Board> cameFrom = new HashMap<>();

    //Calculate Heuristic Cost of Given Board
    public static int calculateCost(Board board) {
        //Cost Function: 100 * # Attacking Queens + Total Movement Costs
        return ((board.findNumAttacks() * 100) + board.getAllMoveCost(refBoard));
    }


    public static Board nextBoard(Board board) {
        Board returnThis = null;
        int boardCost = calculateCost(board);

        ArrayList<ArrayList<QueenEntry>> allMoves = board.allPossibleMoves();

        //if we have already seen board, dont take move

        //Iterate through shuffled queen list
        for (int i = 1; i <= NumQueens; i++) {
            ArrayList<QueenEntry> subsetMoves = allMoves.get(i); // -1 HERE Before

            for (QueenEntry j : subsetMoves) {
                Board tmpBoard = board.moveAQueen(i, j);
                //If this board represents a lower cost accept it, if not dont add to queue
                if(!reachableBoards.contains(tmpBoard)) {
                    reachableBoards.add(tmpBoard);
                    costToReach.put(tmpBoard, calculateCost(tmpBoard));
                    priorityQ.put(tmpBoard, calculateCost(tmpBoard));
                    cameFrom.put(tmpBoard, board);

                } else if(costToReach.get(tmpBoard) > calculateCost(tmpBoard)) {
                    reachableBoards.add(tmpBoard);
                    costToReach.put(tmpBoard, calculateCost(tmpBoard));
                    priorityQ.put(tmpBoard, calculateCost(tmpBoard));
                    cameFrom.put(tmpBoard, board);
                }
            }
            Integer min = Collections.min(priorityQ.values());
            for(Board b : reachableBoards) {
                if(costToReach.get(b).equals(min)) {
                    priorityQ.remove(b, min);
                    returnThis = b;
                }
            }
        }
        return returnThis;
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

        while (true) {
            if (nextBoard(currentBoard).findNumAttacks() != 0) {
                //System.out.println(nextBoard(currentBoard));
                currentBoard = nextBoard(currentBoard);
            } else {
                lowestCost = calculateCost(currentBoard);

                break;
            }
        }

        long end = System.nanoTime();
        System.out.println("Final Board Cost: " + lowestCost);
        System.out.println("Elapsed Time in ms: " + ((end-start)/Math.pow(1,6)));

    }
}
