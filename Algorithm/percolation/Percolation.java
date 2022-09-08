import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private int[] siteval;
    /**
     * The first WeightedQuickUnionUF is used to check percolates
     * The second WeightedQuickUnionUF is used to find full sites
     */
    private WeightedQuickUnionUF grid1;
    private WeightedQuickUnionUF grid2;
    private int size;
    private int open_num;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        // Corner case
        if (n < 0) throw new IllegalArgumentException("n is negative");
        // Initialize
        grid1 = new WeightedQuickUnionUF(n * n + 2);
        grid2 = new WeightedQuickUnionUF(n * n + 2);
        siteval = new int[n * n + 2];
        size = n;
        open_num = 0;
        // Create virtual site ———— difference between grid1 and grid2 due to different function
        for (int i = 1; i < n + 1; i++) {
            grid1.union(0, i);
            grid1.union(n * n + 1, n * n + 1 - i);
            grid2.union(0, i);
        }
    }

    // map 2D position to 1D index
    private int xyto1D(int row, int col) {
        if (!isOutTrue(row, col)) return (row - 1) * size + col;
        else return 0;
    }

    // validate the index
    private void isOut(int row, int col) {
        if (row <= 0 || row > size)
            throw new IndexOutOfBoundsException("row index i out of bounds");
        if (col <= 0 || col > size)
            throw new IndexOutOfBoundsException("col index j out of bounds");
    }

    private boolean isOutTrue(int row, int col) {
        if (row <= 0 || row > size) return true;
        if (col <= 0 || col > size) return true;
        return false;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        isOut(row, col);
        int index = xyto1D(row, col);
        siteval[index] = 1;
        // open sites number increase
        open_num += 1;
        // union sites around it
        int up = xyto1D(row - 1, col);
        int down = xyto1D(row + 1, col);
        int left = xyto1D(row, col - 1);
        int right = xyto1D(row, col + 1);
        if ((!isOutTrue(row - 1, col)) && siteval[up] == 1) {
            grid1.union(index, up);
            grid2.union(index, up);
        }
        if ((!isOutTrue(row + 1, col)) && siteval[down] == 1) {
            grid1.union(index, down);
            grid2.union(index, down);
        }
        if ((!isOutTrue(row, col - 1)) && siteval[left] == 1) {
            grid1.union(index, left);
            grid2.union(index, left);
        }
        if ((!isOutTrue(row, col + 1)) && siteval[right] == 1) {
            grid1.union(index, right);
            grid2.union(index, right);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        isOut(row, col);
        return siteval[xyto1D(row, col)] == 1;
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        isOut(row, col);
        int index = xyto1D(row, col);
        return grid2.connected(index, 0) && siteval[index] == 1;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return open_num;
    }

    // does the system percolate?
    public boolean percolates() {
        return grid1.connected(0, size * size + 1);
    }

    public static void main(String[] args) {
    }
}
