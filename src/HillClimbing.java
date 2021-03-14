import java.util.*;
import java.util.concurrent.TimeUnit;

public class HillClimbing {

    public static Random rand;

    public static final int BoardSize = 256;
    public static final int NumQueens = BoardSize * 9 / 8;
    public static final int maxAllowableResets = 2;
    public static final int maxSideMoves = 10;
    public static double Tmp,StartingTmp = 10000;
    public static final double TempDegradation = 0.25;
    public static final double minAllowableTemp = 1;

    public static Board currentBoard, refBoard, bestBoard;
    public static int currentCost, lowestCost, thisLowestCost, startingCost, sideMoves = 0;


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
        //System.out.println("Current Cost " + boardCost);
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
                int tmpCost = calculateCost(tmpBoard);
                Tmp *= TempDegradation;
                //If this board represents a lower cost accept it, if not accept it with some probability
                if ((boardCost-tmpCost)>15){
//                    System.out.println("Found Better " + Tmp);
                    return tmpBoard;
                }
                else if(((boardCost-tmpCost) < 15) && ((boardCost-tmpCost) >= 0) && sideMoves < maxSideMoves) {
                    sideMoves +=1;
//                    System.out.println("Took Sideways " + Tmp);
                    return tmpBoard;
                }
                else{
                    if((Math.random() < simulatedAnnealing(boardCost, tmpCost, Tmp))&&(Tmp > minAllowableTemp)) {
//                        System.out.println("Took Backwards " + Tmp);
                        return tmpBoard;
                    }
                }
            }
        }
        //null is returned if no better move was discovered
        return null;
    }

    public static void main(String[] args) {
        long startTime = 0, endTime  = 0, elapsedTime, elapsedTimeMs, elapsedTimeS;
        long avgTime = 0,  totalTime = 0;
        int avgCost = 0,totalCost = 0, avgStartCost = 0, totalStartCost = 0;
        int maxRuns = 3;

        for(int runs = 0; runs < maxRuns; runs++) {
            refBoard = Board.ofRandomBoard(BoardSize, NumQueens);
            currentBoard = refBoard;
            currentCost = calculateCost(currentBoard);
            startingCost = currentCost;
            totalStartCost += startingCost;
            System.out.println("Starting Cost " + startingCost);
            lowestCost = currentCost;
            Tmp = StartingTmp;

            for (int i = 0; i < maxAllowableResets; i++) {
                if(i==0){
                    startTime = System.nanoTime();
                }

                while (true) {
                    Board tmpBoard = nextBoard(currentBoard);
                    if (tmpBoard != null) {
                        currentBoard = tmpBoard;
                    } else {
                        thisLowestCost = calculateCost(currentBoard);
                        break;
                    }
                }

                if (thisLowestCost < lowestCost) {
                    lowestCost = thisLowestCost;
                    bestBoard = currentBoard;
                    System.out.println("New Lowest " + lowestCost);
                }

                if (i < maxAllowableResets) {
                    //Reset if we're going to go through again
                    currentBoard = refBoard;
                    Tmp = StartingTmp;
                }
            }
            endTime = System.nanoTime();
            elapsedTime = (endTime - startTime);
            elapsedTimeMs = TimeUnit.MILLISECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
            elapsedTimeS = TimeUnit.SECONDS.convert(elapsedTime, TimeUnit.NANOSECONDS);
            totalTime += elapsedTimeS;
            totalCost += lowestCost;
            System.out.println("Final Board Cost: " + lowestCost);
//            System.out.println("Start Time " + startTime);
//            System.out.println("End Time " + endTime);
//            System.out.println("Elapsed Time in ns: " + elapsedTime);
//            System.out.println("Elapsed Time in ms: " + elapsedTimeMs);
            System.out.println("Elapsed Time in S " + elapsedTimeS);

        }
        avgStartCost = totalStartCost / maxRuns;
        avgCost = totalCost / maxRuns;
        avgTime = totalTime / maxRuns;
        System.out.println("Average Starting Cost " + avgStartCost);
        System.out.println("Average Cost " + avgCost);
        System.out.println("Average Time " + avgTime);

    }
}
