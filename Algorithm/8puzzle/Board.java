/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.LinkedList;

public class Board {
    private int[][] btiles;
    private int n;
    private int hamming;
    private int manhattan;
    private boolean isGoal;
    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)

    private int goaltile(int i, int j) {
        if (i == n - 1 && j == n - 1) return 0;
        return n * i + j + 1;
    }

    public Board(int[][] tiles) {
        n = tiles.length;
        btiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                btiles[i][j] = tiles[i][j];
            }
        }
        hamming = generate_hamming();
        manhattan = generate_manhattan();
    }

    // string representation of this board
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(n + "\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                s.append(String.format("%2d ", btiles[i][j]));
            }
            s.append("\n");
        }
        return s.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    private int generate_hamming() {
        int ham = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (btiles[i][j] != goaltile(i, j) && btiles[i][j] != 0) ham++;
            }
        }
        return ham;
    }

    public int hamming() {
        return hamming;
    }

    // sum of Manhattan distances between tiles and goal
    private int generate_manhattan() {
        int man = 0;
        int goali, goalj;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (btiles[i][j] == 0) continue;
                else if (btiles[i][j] % n == 0) {
                    goali = btiles[i][j] / n - 1;
                    goalj = n - 1;
                }
                else {
                    goali = btiles[i][j] / n;
                    goalj = btiles[i][j] % n - 1;
                }
                man += (Math.abs(i - goali) + Math.abs(j - goalj));
            }
        }
        return man;
    }

    public int manhattan() {
        return manhattan;
    }

    // is this board the goal board?
    public boolean isGoal() {
        if (isGoal) return true;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (btiles[i][j] != goaltile(i, j)) {
                    isGoal = false;
                    return false;
                }
            }
        }
        isGoal = true;
        return true;

    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null) return false;
        if (this.getClass() != y.getClass()) return false;
        Board that = (Board) y;
        if (!Arrays.deepEquals(this.btiles, that.btiles)) return false;
        return true;
    }

    // all neighboring boards
    private int[] findzero() {
        int zeroi = 0;
        int zeroj = 0;
        here:
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (btiles[i][j] == 0) {
                    zeroi = i;
                    zeroj = j;
                    break here;
                }
            }
        }
        return new int[] { zeroi, zeroj };
    }

    private boolean out(int x) {
        if (x < 0 || x >= n) return true;
        return false;
    }

    private Board exch(int i1, int j1, int i2, int j2) {
        if (out(i1) || out(j1) || out(i2) || out(j2)) return null;
        Board newb = new Board(btiles);
        int temp = newb.btiles[i1][j1];
        newb.btiles[i1][j1] = newb.btiles[i2][j2];
        newb.btiles[i2][j2] = temp;
        return newb;
    }

    public Iterable<Board> neighbors() {
        int[] zero = findzero();
        LinkedList<Board> neiboards = new LinkedList<Board>();
        int i = zero[0];
        int j = zero[1];
        if (this.exch(i, j, i, j - 1) != null) neiboards.add(this.exch(i, j, i, j - 1));
        if (this.exch(i, j, i, j + 1) != null) neiboards.add(this.exch(i, j, i, j + 1));
        if (this.exch(i, j, i - 1, j) != null) neiboards.add(this.exch(i, j, i - 1, j));
        if (this.exch(i, j, i + 1, j) != null) neiboards.add(this.exch(i, j, i + 1, j));
        return neiboards;
    }

    // a board that is obtained by exchanging any pair of tiles
    public Board twin() {
        if (this.exch(0, 0, 0, 1) != null && btiles[0][1] != 0 && btiles[0][0] != 0)
            return this.exch(0, 0, 0, 1);
        else if (this.exch(0, 0, 1, 0) != null && btiles[0][0] != 0) return this.exch(0, 0, 1, 0);
        else {
            return this.exch(0, 1, 1, 0);
        }
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // read files
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);

        // test initialize and to string —— bingo
        StdOut.println(initial);

        // test hamming —— OK
        // StdOut.println("the hamming is " + initial.hamming());
        // StdOut.println(initial.isGoal());

        // test manhattan  —— maybe right
        // StdOut.println(initial.manhattan());

        // test neighbors -----right
        for (Board b : initial.neighbors()) {
            StdOut.println(b);
            StdOut.println("the hamming is " + b.hamming);
        }

        // test twins
        // StdOut.println(initial.twin());

        // test isGoal
        // StdOut.println(initial.isGoal());
    }


}