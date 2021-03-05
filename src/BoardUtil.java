import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class BoardUtil {

    public static int[][] deepcopy(int[][] board) {
        return Arrays.stream(board)
                .map(int[]::clone)
                .toArray(int[][]::new);
    }

    public static void randomShuffle(int[] flatBoard) {
        Random rng = ThreadLocalRandom.current();
        for(int i = 0; i < flatBoard.length; i++) {
            int j = rng.nextInt(flatBoard.length - i) + i ;
            int temp = flatBoard[j];
            flatBoard[j] = flatBoard[i];
            flatBoard[i] = temp;
        }
    }

    public static int[] randomWeightList(int queenSize) {
        int[] out = new int[queenSize];
        Random rng = ThreadLocalRandom.current();
        for (int i = 0; i<queenSize; i++) {
            out[i] = rng.nextInt(9) + 1;
        }
        return out;
    }
}
