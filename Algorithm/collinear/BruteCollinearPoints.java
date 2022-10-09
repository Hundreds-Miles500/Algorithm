/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> lines;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Null points array");
        lines = new ArrayList<LineSegment>();
        Arrays.sort(points);
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) throw new IllegalArgumentException("some point is null point.");
            for (int j = i + 1; j < points.length; j++) {
                if (points[i] == points[j]) throw new IllegalArgumentException("Repeat elements");
                for (int k = j + 1; k < points.length; k++) {
                    if (points[i].slopeTo(points[j]) == points[j].slopeTo(points[k])) {
                        for (int h = k + 1; h < points.length; h++) {
                            if (points[j].slopeTo(points[k]) == points[k].slopeTo(points[h])) {
                                lines.add(new LineSegment(points[i], points[h]));
                            }
                        }
                    }
                }
            }
        }
    }

    // the number of line segments
    public int numberOfSegments() {
        return lines.size();
    }

    // the line segments
    // @edu.umd.cs.findbugs.annotations.SuppressFBWarnings("BC_IMPOSSIBLE_DOWNCAST_OF_TOARRAY")
    public LineSegment[] segments() {
        LineSegment[] l = new LineSegment[lines.size()];
        return lines.toArray(l);
    }

    public static void main(String[] args) {
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        // for (Point p : points) {
        //     p.draw();
        // }
        // StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
