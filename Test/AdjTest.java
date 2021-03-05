import static org.junit.Assert.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AdjTest {
    @Test
    public void rowAdjTest1() {
        int[] exRow = {0,0,1};
        List<Integer> a = Arrays.asList(1, 0, 0);
        List<Integer> b = Arrays.asList(0, 1, 0);
        List<List<Integer>> ans = Arrays.asList(a, b);
        assertEquals(ans, Board.adjRows(exRow, exRow.length, 1));
    }

    @Test
    public void rowAdjTest2() {
        int[] exRow = {0,0,1,0,0,0,0,0};
        List<Integer> a = Arrays.asList(1, 0, 0, 0 ,0 ,0 ,0 ,0);
        List<Integer> b = Arrays.asList(0, 1, 0, 0 ,0 ,0 ,0 ,0);
        //List<Integer> c = Arrays.asList(0, 0, 1, 0 ,0 ,0 ,0 ,0);
        List<Integer> d = Arrays.asList(0, 0, 0, 1 ,0 ,0 ,0 ,0);
        List<Integer> e = Arrays.asList(0, 0, 0, 0 ,1 ,0 ,0 ,0);
        List<Integer> f = Arrays.asList(0, 0, 0, 0 ,0 ,1 ,0 ,0);
        List<Integer> g = Arrays.asList(0, 0, 0, 0 ,0 ,0 ,1 ,0);
        List<Integer> h = Arrays.asList(0, 0, 0, 0 ,0 ,0 ,0 ,1);

        List<List<Integer>> ans = Arrays.asList(a, b, d, e, f, g, h);
        assertEquals(ans, Board.adjRows(exRow, exRow.length, 1));
    }

    @Test
    public void boardAdjTest1() {
        int ansSize = 6;
        assertEquals(ansSize, Board.adjBoards(ExampleBoards.exampleBoardZero).size());
    }

    @Test
    public void boardAdjTest2() {
        int ansSize = 12;
        assertEquals(ansSize, Board.adjBoards(ExampleBoards.exampleBoardOne).size());
    }

    @Test
    public void boardAdjTest3() {
        int ansSize = 56;
        assertEquals(ansSize, Board.adjBoards(ExampleBoards.exampleBoardThree).size());
    }
}
