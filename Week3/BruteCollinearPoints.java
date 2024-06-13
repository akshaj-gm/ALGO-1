/* *****************************************************************************
 *  Name:              Akshaj Gupta
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    private int numberoflines = 0;
    private final LineSegment[] fourLS;

    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        for (Point point : points) {
            if (point == null) {
                throw new IllegalArgumentException("Point is null");
            }
        }
        Point[] temp = points.clone();
        Arrays.sort(temp);
        for (int i = 0; i < temp.length - 1; i++) {
            if (temp[i].compareTo(temp[i + 1]) == 0)
                throw new IllegalArgumentException("Duplicate points");
        }

        LineSegment[] tempSegments = new LineSegment[temp.length * temp.length];
        for (int i = 0; i < temp.length; i++) {
            for (int j = i + 1; j < temp.length; j++) {
                for (int k = j + 1; k < temp.length; k++) {
                    for (int s = k + 1; s < temp.length; s++) {
                        if (Double.compare(temp[i].slopeTo(temp[j]), temp[i].slopeTo(temp[k])) == 0
                                &&
                                Double.compare(temp[i].slopeTo(temp[j]), temp[i].slopeTo(temp[s]))
                                        == 0) {
                            tempSegments[numberoflines] = new LineSegment(temp[i], temp[s]);
                            numberoflines += 1;
                        }
                    }
                }
            }
        }

        fourLS = new LineSegment[numberoflines];
        System.arraycopy(tempSegments, 0, fourLS, 0, numberoflines);
    }

    public int numberOfSegments() {
        return numberoflines;
    }

    public LineSegment[] segments() {
        return fourLS.clone();
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
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
