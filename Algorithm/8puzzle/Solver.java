/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;

import java.util.Comparator;
import java.util.LinkedList;

public class Solver {

    private Searchnode lastnode;
    private boolean solvable;

    private class Searchnode {
        private Board board;
        private int moves;
        private Searchnode prinode;

        // constructor
        public Searchnode(Board b) {
            board = b;
            moves = 0;
            prinode = null;
        }

        public Searchnode(Board b, Searchnode pnode) {
            board = b;
            moves = pnode.moves + 1;
            prinode = pnode;
        }

        // build comparator
        private class Byhamming implements Comparator<Searchnode> {
            public int compare(Searchnode o1, Searchnode o2) {
                return Integer.compare(o1.moves + o1.board.hamming(),
                                       o2.moves + o2.board.hamming());
            }
        }

        private class Bymanhattan implements Comparator<Searchnode> {
            public int compare(Searchnode o1, Searchnode o2) {
                return Integer.compare(o1.moves + o1.board.manhattan(),
                                       o2.moves + o2.board.manhattan());
            }
        }

        // generate comparator
        public Comparator<Searchnode> Byhamming() {
            return new Byhamming();
        }

        public Comparator<Searchnode> Bymanhattan() {
            return new Bymanhattan();
        }

    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        // test if the argument is null
        if (initial == null) throw new IllegalArgumentException("Board input is null");

        // create a start node
        Searchnode start = new Searchnode(initial);
        Searchnode twinstart = new Searchnode(initial.twin());

        // create priority queue of Searchnode
        MinPQ<Searchnode> SearchPQ = new MinPQ<Searchnode>(20, start.Bymanhattan());
        SearchPQ.insert(start);
        MinPQ<Searchnode> twinSearchPQ = new MinPQ<Searchnode>(20, twinstart.Bymanhattan());
        twinSearchPQ.insert(twinstart);

        // A* part
        Astar(SearchPQ, twinSearchPQ);
    }

    // A* Algorithm
    private void Astar(MinPQ<Searchnode> SearchPQ, MinPQ<Searchnode> twinSearchPQ) {
        // A* algorithm body
        while (SearchPQ.size() != 0) {
            Searchnode detected = SearchPQ.delMin();
            Searchnode twindetected = twinSearchPQ.delMin();
            // solvable test and test if reach the goal
            if (detected.board.isGoal()) {
                lastnode = detected;
                solvable = true;
                return;
            }
            else if (twindetected.board.isGoal()) {
                solvable = false;
                return;
            }

            // add neighbors to the PQ
            for (Board b : detected.board.neighbors()) {
                if (detected.prinode != null && b.equals(detected.prinode.board)) continue;
                SearchPQ.insert(new Searchnode(b, detected));
            }
            for (Board tb : twindetected.board.neighbors()) {
                if (twindetected.prinode != null && tb.equals(twindetected.prinode.board)) continue;
                twinSearchPQ.insert(new Searchnode(tb, twindetected));
            }
        }
    }


    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return solvable;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        if (solvable) return lastnode.moves;
        else return -1;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        LinkedList<Board> Boards = new LinkedList<Board>();
        Searchnode node = lastnode;
        if (solvable) {
            while (node != null) {
                Boards.addFirst(node.board);
                node = node.prinode;
            }
            return Boards;
        }
        else {
            return null;
        }
    }


    public static void main(String[] args) {

    }
}
