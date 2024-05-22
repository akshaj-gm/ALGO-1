/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private double[] frac;
    private int len;
    private int trial;

    public PercolationStats(int n, int trials) {
        len = n;
        trial = trials;
        frac = new double[trial];
        for (int i = 0; i < trial; i++) {
            Percolation pr = new Percolation(len);
            while (!pr.percolates()) {
                int num = StdRandom.uniformInt(len * len - 1);
                int row = num / len + 1;
                int col = num % len + 1;
                pr.open(row, col);
            }
            frac[i] = (double) pr.numberOfOpenSites() / (n * n);
        }
    }

    public double mean() {
        return StdStats.mean(frac);
    }

    public double stddev() {
        return StdStats.stddev(frac);
    }

    public double confidenceLo() {
        return mean() - (1.96 * stddev() / Math.sqrt(trial));
    }

    public double confidenceHi() {
        return mean() + (1.96 * stddev() / Math.sqrt(trial));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int trials = Integer.parseInt(args[1]);
        PercolationStats pStats = new PercolationStats(n, trials);

        String confidence = pStats.confidenceLo() + ", "
                + pStats.confidenceHi();
        StdOut.println("mean                    = " + pStats.mean());
        StdOut.println("stddev                  = " + pStats.stddev());
        StdOut.println("95% confidence interval = " + confidence);
    }
}
