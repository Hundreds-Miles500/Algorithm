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

public class FastCollinearPoints {
    private ArrayList<LineSegment> lines;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Null points array");
        lines = new ArrayList<LineSegment>();
        int counts = 0;
        Point start, end;
        Point[] points2 = points.clone();
        // test repeated points
        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i] == points[j]) throw new IllegalArgumentException("repeated points");
            }
        }
        // find all line segments
        for (int i = 0; i < points.length; i++) {
            if (points2[i] == null) throw new IllegalArgumentException("some point is null");
            Arrays.sort(points, points2[i].slopeOrder());
            start = points[0];
            end = points[0];
            for (int j = 0; j < points.length - 1; j++) {
                if (points[0].slopeTo(points[j]) == points[0].slopeTo(points[j + 1])) {
                    counts++;
                    if (start.compareTo(points[j]) > 0) start = points[j];
                    if (start.compareTo(points[j + 1]) > 0) start = points[j + 1];
                    if (end.compareTo(points[j]) < 0) end = points[j];
                    if (end.compareTo(points[j + 1]) < 0) end = points[j + 1];
                }
                if (points[0].slopeTo(points[j]) != points[0].slopeTo(points[j + 1])
                        || j + 2 == points.length) {
                    if (counts + 2 >= 4 && start == points[0]) {
                        lines.add(new LineSegment(start, end));
                        counts = 0;
                        start = points[0];
                        end = points[0];
                    }
                    else {
                        counts = 0;
                        start = points[0];
                        end = points[0];
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
