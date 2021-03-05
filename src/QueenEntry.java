import java.util.Objects;

//This is an immutable 3-element tuple for the position and weight of a Queen.
//So a clone of ArrayList<QueenEntry> is safe.
public class QueenEntry {
    private int row, col, weight;

    @Override
    public String toString() {
        return "QueenEntry{" +
                "row=" + row +
                ", col=" + col +
                ", weight=" + weight +
                '}';
    }

    private QueenEntry(int row, int col, int weight) {
        this.row = row;
        this.col = col;
        this.weight = weight;
    }

    public static QueenEntry of(int row, int col, int weight) {
        return new QueenEntry(row, col, weight);
    }

    public static QueenEntry of(int row, int col) {
        return of(row, col, 0);
    }

    public QueenEntry clone(QueenEntry queen) {
        return queen;
    }

    public QueenEntry setPos(int row, int col) {
        if (row == this.row && col == this.col) {
            return this;
        }
        return of(row, col, this.weight);
    }

    public QueenEntry set(int row, int col, int weight) {
        if (this.weight == weight) {
            return setPos(row, col);
        }
        return of(row, col, this.weight);
    }

    public QueenEntry setWeight(int weight) {
        if (this.weight == weight) {
            return this;
        }
        return of(row, col, this.weight);
    }


    public int row() {
        return row;
    }

    public int col() {
        return col;
    }

    public int weight() {
        return weight;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QueenEntry that = (QueenEntry) o;
        return row == that.row && col == that.col && weight == that.weight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col, weight);
    }
}
