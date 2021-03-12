import java.util.*;

public class HillClimbing {

    public static Random rand;

    public static final int BoardSize = 16;
    public static final int NumQueens = BoardSize * 9 / 8;
    public static final int maxAllowableResets = 5;
    public static double Tmp,StartingTmp = 10000;
    public static final double TempDegradation = 0.5;
    public static final double minAllowableTemp = 1;

    public static Board currentBoard, refBoard, bestBoard;
    public static int currentCost, lowestCost, thisLowestCost, startingCost;

    //Calculate Heuristic Cost of Given Board
    public static int calculateCost(Board board) {
        //Cost Function: 100 * # Attacking Queens + Total Movement Costs
        return ((board.findNumAttacks() * 100) + board.getAllMoveCost(refBoard));
    }

    public static double simulatedAnnealing(int curSol, int newSol, double T){
        return Math.exp((curSol-newSol)/T);
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
            ArrayList<QueenEntry> subsetMoves = allMoves.get(queenID); // -1 HERE Before

            //Iterate through all possible moves for "queenID-th" queen
            for (QueenEntry i : subsetMoves) {
                Board tmpBoard = board.moveAQueen(queenID, i);
                //If this board represents a lower cost accept it, if not accept it with some probability
                if (calculateCost(tmpBoard) < boardCost){
                    //System.out.println("Found Better " + Tmp);
                    Tmp *= TempDegradation;
                    return tmpBoard;
                }
                else {
                    if ((Math.random() < simulatedAnnealing(boardCost, calculateCost(tmpBoard), Tmp))&&(Tmp > minAllowableTemp)){
                        //System.out.println("Took Backwards " + Tmp);
                        Tmp *= TempDegradation;
                        return tmpBoard;
                    }
                }

            }
        }
        Tmp *= TempDegradation;
        //null is returned if no better move was discovered
        return null;
    }

    public static void main(String[] args) {
        double avgTime = 0, avgCost =0;
        int numRuns = 500;
        for(int runs = 0; runs < numRuns; runs++)
        {
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
                        System.out.println(nextBoard(currentBoard));
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
                    //Reset if we're going to go through again
                    currentBoard = refBoard;
                    Tmp = StartingTmp;
                }
            }
            long end = System.nanoTime();
            double elapsedTime = ((end-start)/Math.pow(1,6));
            System.out.println("Final Board Cost: " + lowestCost);
            System.out.println("Elapsed Time in ms: " + elapsedTime);

            avgTime += elapsedTime;
            avgCost += lowestCost;
        }
        avgCost = avgCost / numRuns;
        avgTime = avgTime / numRuns;
        System.out.println("Average Cost: " + avgCost);
        System.out.println("Average Runtime: " + avgCost);
    }
}
