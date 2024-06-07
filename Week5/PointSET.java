/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class PointSET {
    private SET<Point2D> points;

    public PointSET() {
        this.points = new SET<>();
    }

    public boolean isEmpty() {
        return points.isEmpty();
    }

    public int size() {
        return points.size();
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null");
        }
        if (!points.contains(p)) {
            points.add(p);
        }
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null");
        }
        return points.contains(p);
    }

    public void draw() {
        for (Point2D point : points) {
            point.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("Rectangle cannot be null");
        }
        SET<Point2D> rangeSet = new SET<>();
        for (Point2D point : points) {
            if (rect.contains(point)) {
                rangeSet.add(point);
            }
        }
        return rangeSet;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point cannot be null");
        }
        if (points.isEmpty()) {
            return null;
        }

        Point2D nearestPoint = null;
        double champ = Double.POSITIVE_INFINITY;

        for (Point2D point : points) {
            double distance = point.distanceTo(p);
            if (distance < champ) {
                champ = distance;
                nearestPoint = point;
            }
        }
        return nearestPoint;
    }

    public static void main(String[] args) {
        PointSET set = new PointSET();

        // Insert some points
        set.insert(new Point2D(0.5, 0.5));
        set.insert(new Point2D(0.2, 0.7));
        set.insert(new Point2D(0.8, 0.3));

        // Test isEmpty and size methods
        System.out.println("Is set empty? " + set.isEmpty());
        System.out.println("Number of points in the set: " + set.size());

        // Test contains method
        Point2D testPoint = new Point2D(0.5, 0.5);
        System.out.println("Does the set contain " + testPoint + "? " + set.contains(testPoint));

        // Test range method
        RectHV rect = new RectHV(0.0, 0.0, 0.6, 0.6);
        System.out.println("Points within the rectangle " + rect + ":");
        for (Point2D p : set.range(rect)) {
            System.out.println(p);
        }

        // Test nearest method
        Point2D queryPoint = new Point2D(0.6, 0.6);
        Point2D nearestPoint = set.nearest(queryPoint);
        System.out.println("Nearest point to " + queryPoint + ": " + nearestPoint);
    }
}

