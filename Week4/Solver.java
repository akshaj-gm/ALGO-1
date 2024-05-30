/* *****************************************************************************
 *  Name:              Akshaj Gupta
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Solver {
    private static class SearchNode {
        private final Board board;
        private final int moves;
        private final SearchNode previous;

        public SearchNode(Board board, int moves, SearchNode previous) {
            this.board = board;
            this.moves = moves;
            this.previous = previous;
        }

        public int priority() {
            return board.manhattan() + moves;
        }
    }

    private class ManhattanComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode n1, SearchNode n2) {
            return Integer.compare(n1.priority(), n2.priority());
        }
    }

    private int move = 0;
    private List<Board> sol;
    private boolean solvable;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        sol = new ArrayList<>();
        MinPQ<SearchNode> a_star = new MinPQ<>(new ManhattanComparator());
        a_star.insert(new SearchNode(initial, 0, null));

        while (!a_star.isEmpty()) {
            SearchNode current = a_star.delMin();
            if (current.board.isGoal()) {
                move = current.moves;
                while (current != null) {
                    sol.add(current.board);
                    current = current.previous;
                }
                Collections.reverse(sol);
                solvable = true;
                return;
            }
            for (Board neighbor : current.board.neighbors()) {
                if (current.previous == null || !neighbor.equals(current.previous.board)) {
                    a_star.insert(new SearchNode(neighbor, current.moves + 1, current));
                }
            }
        }
        solvable = false;
        move = -1;
        sol = null;
    }

    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return move;
    }

    public Iterable<Board> solution() {
        return sol;
    }

    // test client (see below)
    public static void main(String[] args) {
        int[][] initialTiles = {
                { 13, 11, 9, 3 },
                { 14, 7, 1, 4 },
                { 0, 5, 10, 12 },
                { 15, 2, 6, 8 }
        };

        Board initialBoard = new Board(initialTiles);
        Solver solver = new Solver(initialBoard);

        if (solver.isSolvable()) {
            System.out.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                System.out.println(board);
            }
        }
        else {
            System.out.println("No solution possible");
        }
    }
}

