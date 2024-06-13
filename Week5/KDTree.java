/* *****************************************************************************
 *  Name:              Akshaj Gupta
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.ArrayList;
import java.util.List;

public class KDTree {
    private Node root;
    private static final boolean y_axis = true;
    private static final boolean x_axis = false;
    private int size;

    private static class Node {
        private final Point2D point;
        private final RectHV rect;
        private Node left, right;
        private final boolean orientation;

        public Node(Point2D p, RectHV rect, boolean orientation) {
            this.point = p;
            this.rect = rect;
            this.orientation = orientation;
        }
    }

    public KDTree() {
        root = null;
        size = 0;
    }

    public boolean isEmpty() {
        return root == null;
    }

    public int size() {
        return size;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("no null");
        }
        root = insert(root, y_axis, p, 0, 0, 1, 1);
        size++;
    }

    private Node insert(Node node, boolean orientation, Point2D p, double xmin, double ymin,
                        double xmax, double ymax) {
        if (node == null) {
            return new Node(p, new RectHV(xmin, ymin, xmax, ymax), orientation);
        }
        if (node.point.equals(p)) return node;
        if (orientation == y_axis) {
            if (p.x() < node.point.x())
                node.left = insert(node.left, x_axis, p, xmin, ymin, node.point.x(), ymax);
            else
                node.right = insert(node.right, x_axis, p, node.point.x(), ymin, xmax, ymax);
        }
        else {
            if (p.y() < node.point.y())
                node.left = insert(node.left, y_axis, p, xmin, ymin, xmax, node.point.y());
            else
                node.right = insert(node.right, y_axis, p, xmin, node.point.y(), xmax, ymax);
        }
        return node;
    }

    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException("argument to contains() is null");
        return contains(root, y_axis, p);
    }

    private boolean contains(Node node, boolean orientation, Point2D p) {
        if (node == null) return false;
        if (node.point.equals(p)) return true;

        if (orientation == y_axis) {
            if (p.x() < node.point.x())
                return contains(node.left, x_axis, p);
            else
                return contains(node.right, x_axis, p);
        }
        else {
            if (p.y() < node.point.y())
                return contains(node.left, y_axis, p);
            else
                return contains(node.right, y_axis, p);
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException("argument to range() is null");
        List<Point2D> result = new ArrayList<>();
        range(root, rect, result);
        return result;
    }

    private void range(Node node, RectHV rect, List<Point2D> result) {
        if (node == null) return;
        if (rect.contains(node.point)) result.add(node.point);
        if (node.left != null && rect.intersects(node.left.rect))
            range(node.left, rect, result);
        if (node.right != null && rect.intersects(node.right.rect))
            range(node.right, rect, result);
    }

    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException("argument to nearest() is null");
        if (root == null) return null;
        return nearest(root, p, root.point);
    }

    private Point2D nearest(Node node, Point2D p, Point2D nearest) {
        if (node == null) return nearest;
        if (node.point.distanceSquaredTo(p) < nearest.distanceSquaredTo(p))
            nearest = node.point;

        if (node.left != null && node.left.rect.distanceSquaredTo(p) < nearest.distanceSquaredTo(p))
            nearest = nearest(node.left, p, nearest);
        if (node.right != null && node.right.rect.distanceSquaredTo(p) < nearest.distanceSquaredTo(
                p))
            nearest = nearest(node.right, p, nearest);

        return nearest;
    }

    public void draw() {
        StdDraw.clear();
        draw(root);
    }

    private void draw(Node node) {
        if (node == null) return;

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        node.point.draw();

        StdDraw.setPenRadius();
        if (node.orientation == y_axis) {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.line(node.point.x(), node.rect.ymin(), node.point.x(), node.rect.ymax());
        }
        else {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.line(node.rect.xmin(), node.point.y(), node.rect.xmax(), node.point.y());
        }

        draw(node.left);
        draw(node.right);
    }

    public static void main(String[] args) {
        // Choose one of the visualizers to run
        // Uncomment the desired visualizer to test

        // runKdTreeVisualizer();
        runRangeSearchVisualizer(args);
        // runNearestNeighborVisualizer(args);
    }

    private static void runKdTreeVisualizer() {
        KDTree kdtree = new KDTree();
        StdDraw.enableDoubleBuffering();

        while (true) {
            if (StdDraw.mousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                Point2D p = new Point2D(x, y);
                if (!kdtree.contains(p)) {
                    kdtree.insert(p);
                    StdDraw.clear();
                    kdtree.draw();
                    StdDraw.show();
                }
                StdDraw.pause(50);
            }
        }
    }

    private static void runRangeSearchVisualizer(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Expected one argument: filename");
        }

        String filename = args[0];
        In in = new In(filename);
        KDTree kdtree = new KDTree();

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }

        StdDraw.enableDoubleBuffering();

        while (true) {
            if (StdDraw.mousePressed()) {
                double x0 = StdDraw.mouseX();
                double y0 = StdDraw.mouseY();

                while (StdDraw.mousePressed()) {
                    // Keep track of the rectangle as the mouse is dragged
                    double x1 = StdDraw.mouseX();
                    double y1 = StdDraw.mouseY();
                    RectHV rect = new RectHV(Math.min(x0, x1), Math.min(y0, y1), Math.max(x0, x1),
                                             Math.max(y0, y1));

                    StdDraw.clear();
                    kdtree.draw();
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.setPenRadius();
                    rect.draw();

                    Iterable<Point2D> range = kdtree.range(rect);
                    for (Point2D p : range) {
                        StdDraw.setPenColor(StdDraw.BLUE);
                        StdDraw.setPenRadius(0.02);
                        p.draw();
                    }

                    StdDraw.show();
                    StdDraw.pause(50);
                }
            }
        }
    }


    private static void runNearestNeighborVisualizer(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException("Expected one argument: filename");
        }

        String filename = args[0];
        In in = new In(filename);
        KDTree kdtree = new KDTree();

        while (!in.isEmpty()) {
            double x = in.readDouble();
            double y = in.readDouble();
            Point2D p = new Point2D(x, y);
            kdtree.insert(p);
        }

        StdDraw.enableDoubleBuffering();

        while (true) {
            if (StdDraw.mousePressed()) {
                double x = StdDraw.mouseX();
                double y = StdDraw.mouseY();
                Point2D query = new Point2D(x, y);
                Point2D nearest = kdtree.nearest(query);
                StdDraw.clear();
                kdtree.draw();
                StdDraw.setPenColor(StdDraw.RED);
                StdDraw.setPenRadius(0.02);
                nearest.draw();
                StdDraw.show();
                StdDraw.pause(50);
            }
        }
    }
}

