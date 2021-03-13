import java.util.*;

public class AStar {


    public static final int BoardSize = 8;
    public static final int NumQueens = BoardSize;

    public static Board currentBoard, refBoard, bestBoard;
    public static int currentCost, lowestCost, thisLowestCost, startingCost;

    public static Queue<Board> toVisit = new PriorityQueue<>(10000, new BoardComparator());
    public static Set<Board> visitedBoards = new HashSet<>();
    public static HashMap<Board, Integer> costToReach = new HashMap<>();
    public static HashMap<Board, Board> cameFrom = new HashMap<>();

    static class BoardComparator implements Comparator<Board> {
        // Overriding compare()method of Comparator
        // for descending order of cgpa
        public int compare(Board s1, Board s2) {
            if (calculateCost(s1) < calculateCost(s2))
                return -1;
            else if (calculateCost(s1) > calculateCost(s2))
                return 1;
            return 0;
        }
    }

    static class QueenComparator implements Comparator<QueenEntry> {
        // Overriding compare()method of Comparator
        // for descending order of cgpa
        public int compare(QueenEntry s1, QueenEntry s2) {
            if (s1.weight() < s2.weight())
                return 1;
            else if (s1.weight() > s2.weight())
                return -1;
            return 0;
        }
    }

    //Calculate Heuristic Cost of Given Board
    public static int calculateCost(Board board) {
        int weightSum = 0;
        TreeSet<QueenEntry> uniqueQueens = new TreeSet(new QueenComparator());
        uniqueQueens.addAll(board.findConflictingQueens());
        for(int i = 0; i < uniqueQueens.size()/2; i ++){//for half of all queens conflicting
            int aWeight = uniqueQueens.pollFirst().weight();
            weightSum += aWeight * aWeight;
        }
        //return weightSum;
        //Cost Function: 100 * # Attacking Queens + Total Movement Costs
        return (board.findNumAttacks()*100 + board.getAllMoveCost(refBoard));
    }


    public static void nextBoard(Board board) {
        int index = 0;
        Board returnThis = null;
        visitedBoards.add(board);

        ArrayList<ArrayList<QueenEntry>> allMoves = board.allPossibleMoves();

        //if we have already seen board, dont take move

        //Iterate through shuffled queen list
        for (int i = 1; i <= NumQueens; i++) {
            ArrayList<QueenEntry> subsetMoves = allMoves.get(i); // -1 HERE Before
            for (QueenEntry j : subsetMoves) {
                index++;
                Board tmpBoard = board.moveAQueen(i, j);
                //If this board represents a lower cost accept it, if not dont add to queue
                if(!visitedBoards.contains(tmpBoard)) {
                    toVisit.add(tmpBoard);
                    cameFrom.put(tmpBoard, board);
                    costToReach.put(tmpBoard, calculateCost(tmpBoard));

                } else if(costToReach.get(tmpBoard) > calculateCost(tmpBoard) && !cameFrom.get(tmpBoard).equals(board)) {
                    toVisit.add(tmpBoard);
                    visitedBoards.remove(tmpBoard);
                    cameFrom.put(tmpBoard, board);
                    costToReach.put(tmpBoard, calculateCost(tmpBoard));
                }
            }
        }
        //return returnThis;
    }

    public static void main(String[] args) {

        //First Choice Rn
        refBoard = Board.ofRandomBoard(8, NumQueens);
        currentBoard = refBoard;
        currentCost = calculateCost(currentBoard);
        startingCost = currentCost;
        System.out.println(refBoard);
        System.out.println("Starting Cost " + startingCost);
        lowestCost = currentCost;
        toVisit.add(currentBoard);

        long start = System.nanoTime();
        int index = 0;
        while (true) {
            if (currentBoard.findNumAttacks() != 0 && ((System.nanoTime()-start)/Math.pow(1,6)) < 20.000000E8 ) {
                index++;
                //System.out.println(nextBoard(currentBoard));
                nextBoard(toVisit.poll());
                currentBoard = toVisit.peek();
            } else {
                lowestCost = calculateCost(currentBoard);

                break;
            }
        }


        long end = System.nanoTime();
        System.out.println("Final Board Cost: " + lowestCost);
        System.out.println(currentBoard);
        System.out.println("number of runs " + index );
        System.out.println("Elapsed Time in ms: " + ((end-start)/Math.pow(1,6)));

    }
}
