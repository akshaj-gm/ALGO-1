/* *****************************************************************************
 *  Name:              Akshaj Gupta
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private boolean[][] grid; // grid[i][j] = site at row i, column j
    private int len; // length of the grid
    private int top = 0; // virtual top
    private int bottom; // virtual bottom
    private WeightedQuickUnionUF uf; // a WeightedQuickUnionUF instance

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException();
        }
        len = n;
        grid = new boolean[n][n];
        bottom = n * n + 1;
        uf = new WeightedQuickUnionUF(n * n + 2);
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || row > len || col < 1 || col > len) {
            throw new IllegalArgumentException();
        }
        grid[row - 1][col - 1] = true;
        if (row == 1) {
            uf.union(index(row, col), top);
        }
        if (row == len) {
            uf.union(index(row, col), bottom);
        }
        if (row > 1 && isOpen(row - 1, col)) {
            uf.union(index(row, col), index(row - 1, col));
        }
        if (row < len && isOpen(row + 1, col)) {
            uf.union(index(row, col), index(row + 1, col));
        }
        if (col > 1 && isOpen(row, col - 1)) {
            uf.union(index(row, col), index(row, col - 1));
        }
        if (col < len && isOpen(row, col + 1)) {
            uf.union(index(row, col), index(row, col + 1));
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || row > len || col < 1 || col > len) {
            throw new IllegalArgumentException();
        }
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || row > len || col < 1 || col > len) {
            throw new IllegalArgumentException();
        }
        return uf.find(index(row, col)) == uf.find(top);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (isOpen(i + 1, j + 1)) {
                    count += 1;
                }
            }
        }
        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        return uf.find(bottom) == uf.find(top);
    }

    // Return the index at site(i, j)
    private int index(int i, int j) {
        return len * (i - 1) + j;
    }

    // test client (optional)
    public static void main(String[] args) {
        // Test your implementation if needed
    }
}
