/* *****************************************************************************
 *  Name:              Akshaj Gupta
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    private final int size;
    private final int[][] tiles;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        size = tiles.length;
        this.tiles = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                this.tiles[i][j] = tiles[i][j];
            }
        }
    }

    // string representation of this board
    public String toString() {
        StringBuilder info = new StringBuilder();
        info.append(size).append("\n");
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                info.append(tiles[i][j]);
                if (j < size - 1) info.append(" ");
            }
            info.append("\n");
        }
        return info.toString();
    }

    // board dimension n
    public int dimension() {
        return size;
    }

    // number of tiles out of place
    public int hamming() {
        int count = 0;
        int value = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] != 0 && tiles[i][j] != value) {
                    count++;
                }
                value++;
            }
        }
        return count;
    }

    // sum of Manhattan distances between tiles and goal
    public int manhattan() {
        int distance = 0;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] != 0) {
                    int goalRow = (tiles[i][j] - 1) / size;
                    int goalCol = (tiles[i][j] - 1) % size;
                    distance += Math.abs(i - goalRow) + Math.abs(j - goalCol);
                }
            }
        }
        return distance;
    }

    // is this board the goal board?
    public boolean isGoal() {
        int value = 1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] != 0 && tiles[i][j] != value) {
                    return false;
                }
                value++;
            }
        }
        return tiles[size - 1][size - 1] == 0;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (this == y) return true;
        if (y == null || getClass() != y.getClass()) return false;
        Board board = (Board) y;
        return Arrays.deepEquals(tiles, board.tiles);
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        List<Board> neighborsList = new ArrayList<>();
        int blankRow = -1, blankCol = -1;
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tiles[i][j] == 0) {
                    blankRow = i;
                    blankCol = j;
                    break;
                }
            }
        }
        int[][] directions = {
                { 1, 0 },
                { -1, 0 },
                { 0, 1 },
                { 0, -1 }
        };
        for (int[] direction : directions) {
            int newRow = blankRow + direction[0];
            int newCol = blankCol + direction[1];
            if (newRow >= 0 && newRow < size && newCol >= 0 && newCol < size) {
                // Create a copy of the current board
                int[][] newTiles = new int[size][size];
                for (int i = 0; i < size; i++) {
                    System.arraycopy(tiles[i], 0, newTiles[i], 0, size);
                }
                newTiles[blankRow][blankCol] = newTiles[newRow][newCol];
                newTiles[newRow][newCol] = 0;
                neighborsList.add(new Board(newTiles));
            }
        }
        return neighborsList;
    }

    public Board twin() {
        int[][] twinTiles = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                twinTiles[i][j] = tiles[i][j];
            }
        }
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size - 1; j++) {
                if (twinTiles[i][j] != 0 && twinTiles[i][j + 1] != 0) {
                    int temp = twinTiles[i][j];
                    twinTiles[i][j] = twinTiles[i][j + 1];
                    twinTiles[i][j + 1] = temp;
                    return new Board(twinTiles);
                }
            }
        }
        return null;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        int[][] initialTiles = {
                { 1, 2, 3 },
                { 4, 5, 6 },
                { 7, 0, 8 }
        };
        Board board = new Board(initialTiles);
        // Print the board
        System.out.println("Initial Board:");
        System.out.println(board);

        // Print the dimension of the board
        System.out.println("Dimension of the board:");
        System.out.println(board.dimension());

        // Print the Hamming distance
        System.out.println("Hamming distance:");
        System.out.println(board.hamming());

        // Print the Manhattan distance
        System.out.println("Manhattan distance:");
        System.out.println(board.manhattan());

        // Check if the board is the goal board
        System.out.println("Is the board the goal board?");
        System.out.println(board.isGoal());

        // Generate and print all neighboring boards
        System.out.println("Neighbors of the board:");
        for (Board neighbor : board.neighbors()) {
            System.out.println(neighbor);
        }
        // Generate and print the twin board
        System.out.println("Twin board:");
        Board twinBoard = board.twin();
        System.out.println(twinBoard);
    }
}
