import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation2 {
    private boolean[] siteopened;
    private boolean[] bottomconnected;
    private WeightedQuickUnionUF grid;
    private int size;
    private int open_num;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation2(int n) {
        // Corner case
        if (n <= 0) throw new IllegalArgumentException("n is negative");
        // Initialize
        grid = new WeightedQuickUnionUF(n * n + 2);
        siteopened = new boolean[n * n + 2];
        bottomconnected = new boolean[n * n + 2];
        size = n;
        open_num = 0;
    }

    // map 2D position to 1D index
    private int xyto1D(int row, int col) {
        if (!isOutTrue(row, col)) return (row - 1) * size + col;
        else return 0;
    }

    // validate the index
    private void isOut(int row, int col) {
        if (row <= 0 || row > size)
            throw new IllegalArgumentException("row index i out of bounds");
        if (col <= 0 || col > size)
            throw new IllegalArgumentException("col index j out of bounds");
    }

    private boolean isOutTrue(int row, int col) {
        if (row <= 0 || row > size) return true;
        if (col <= 0 || col > size) return true;
        return false;
    }

    // new boolean union-find data structure
    private void union(int p, int q) {
        if (bottomconnected[grid.find(p)] || bottomconnected[grid.find(q)]) {
            bottomconnected[grid.find(p)] = true;
            bottomconnected[grid.find(q)] = true;
            grid.union(p, q);
        }
        else grid.union(p, q);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        isOut(row, col);
        // check for double open
        if (isOpen(row, col)) return;
        // map (row, col) to index
        int index = xyto1D(row, col);
        siteopened[index] = true;
        // open sites number increase
        open_num += 1;
        // connect top row site to top vital site
        if (row == 1) union(0, index);
        // set bottom row to be true
        if (row == size) bottomconnected[index] = true;
        // union sites around it
        int up = xyto1D(row - 1, col);
        int down = xyto1D(row + 1, col);
        int left = xyto1D(row, col - 1);
        int right = xyto1D(row, col + 1);
        if ((!isOutTrue(row - 1, col)) && siteopened[up]) {
            union(index, up);
        }
        if ((!isOutTrue(row + 1, col)) && siteopened[down]) {
            union(index, down);
        }
        if ((!isOutTrue(row, col - 1)) && siteopened[left]) {
            union(index, left);
        }
        if ((!isOutTrue(row, col + 1)) && siteopened[right]) {
            union(index, right);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        isOut(row, col);
        int index = xyto1D(row, col);
        return siteopened[index];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        isOut(row, col);
        int index = xyto1D(row, col);
        return (grid.find(index) == grid.find(0)) && siteopened[index];
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return open_num;
    }

    // does the system percolate?
    public boolean percolates() {
        if (size == 1) return isFull(1, 1);
        return bottomconnected[grid.find(0)];
    }

    public static void main(String[] args) {
        Percolation2 per = new Percolation2(3);
        per.open(1, 1);
        StdOut.println(per.grid.find(2));
    }
}