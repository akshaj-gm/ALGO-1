/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class FastCollinearPoints {
    private final LineSegment[] segments;
    private int numberOfSegments = 0;

    public FastCollinearPoints(Point[] points) {
        if (points == null) throw new IllegalArgumentException("Input points array cannot be null");
        // Check for null points and duplicates
        int len = points.length;
        for (int i = 0; i < len; i++) {
            if (points[i] == null) throw new IllegalArgumentException("Point cannot be null");
        }
        // Check for duplicate points
        Point[] pointsCopy = points.clone();
        Arrays.sort(pointsCopy);
        for (int i = 1; i < len; i++) {
            if (pointsCopy[i].compareTo(pointsCopy[i - 1]) == 0) {
                throw new IllegalArgumentException("Duplicate points found");
            }
        }

        segments = new LineSegment[len * (len - 1) / 2]; // upper bound on number of segments

        for (int i = 0; i < len; i++) {
            Point[] slopeSortedPoints = points.clone();
            Arrays.sort(slopeSortedPoints, points[i].slopeOrder());

            int j = 1;
            while (j < len) {
                int count = 1;
                double slope = points[i].slopeTo(slopeSortedPoints[j]);
                while (j + count < len
                        && Double.compare(slope, points[i].slopeTo(slopeSortedPoints[j + count]))
                        == 0) {
                    count++;
                }
                if (count >= 3) {
                    // Found 3 or more points with the same slope
                    Point[] collinearPoints = new Point[count + 1];
                    collinearPoints[0] = points[i];
                    for (int k = 0; k < count; k++) {
                        collinearPoints[k + 1] = slopeSortedPoints[j + k];
                    }
                    Arrays.sort(collinearPoints);
                    if (points[i].compareTo(collinearPoints[0]) == 0) {
                        segments[numberOfSegments++] = new LineSegment(collinearPoints[0],
                                                                       collinearPoints[count]);
                    }
                }
                j += count;
            }
        }
    }

    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        return Arrays.copyOf(segments, numberOfSegments);
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}

