/* *****************************************************************************
 *  Name:              Alan Turing
 *  Coursera User ID:  123456
 *  Last modified:     1/1/2019
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private int size;
    private double[] perarrays;
    private int pernum;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        isSmaller0(n, trials);
        size = n;
        perarrays = new double[trials];
        pernum = trials;
    }

    // check n and trials
    private void isSmaller0(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new IllegalArgumentException("argument is smaller than 0");
        }
    }

    // test one Percolation progress
    private int percolation(int n) {
        Percolation per = new Percolation(n);
        while (true) {
            int x = StdRandom.uniform(1, n + 1);
            int y = StdRandom.uniform(1, n + 1);
            if (!per.isOpen(x, y)) per.open(x, y);
            if (per.percolates()) {
                return per.numberOfOpenSites();
            }
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(perarrays);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(perarrays);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        double mean = mean();
        double std = stddev();
        double sqrtT = Math.sqrt(pernum);
        return mean - 1.96 * std / sqrtT;
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        double mean = mean();
        double std = stddev();
        double sqrtT = Math.sqrt(pernum);
        return mean + 1.96 * std / sqrtT;
    }

    // Monte Carlo simulation
    private void Monte_Carlo() {
        double totalnum = size * size;
        for (int i = 0; i < pernum; i++) {
            perarrays[i] = percolation(size) / totalnum;
        }
    }

    public static void main(String[] args) {
        int T = Integer.parseInt(args[0]);
        int n = Integer.parseInt(args[1]);
        PercolationStats perstats = new PercolationStats(n, T);
        perstats.Monte_Carlo();
        StdOut.println("mean = " + perstats.mean());
        StdOut.println("stddev = " + perstats.stddev());
        StdOut.println("95% confidence interval = [" + perstats.confidenceLo() + ", "
                               + perstats.confidenceHi() + "]");
    }
}
