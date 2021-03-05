public class ExampleBoards {
    public static final int[][] exampleBoardOne =
                    {{ 0,  0,  0,  1},
                    { 0,  1,  0,  0},
                    { 1,  0,  0,  0},
                    { 0,  0,  1,  0}};

    public static final int[][] exampleBoardTwo = //heuristic = 4
                    {{ 0,  0,  0,  0, 0},
                    { 0,  1,  0,  1, 1},
                    { 1,  0,  0,  0, 0},
                    { 0,  0,  0,  0, 0},
                    { 0,  0,  1,  0, 0}};

    public static final int[][] exampleBoardThree = //heuristic = 8
                    {{ 1, 0, 0, 0, 0, 0, 0, 0},
                    { 0, 1, 0, 0, 0, 0, 0, 0},
                    { 0, 0, 1, 0, 0, 0, 0, 0},
                    { 0, 0, 0, 1, 0, 0, 0, 0},
                    { 0, 0, 0, 0, 1, 0, 0, 0},
                    { 0, 0, 0, 0, 0, 1, 0, 0},
                    { 0, 0, 0, 0, 0, 0, 1, 0},
                    { 0, 0, 0, 0, 0, 0, 0, 1}};
}
