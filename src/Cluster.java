import java.util.ArrayList;

public class Cluster {
    private ArrayList<Point> points;
    private Point center;
    private Point oldCenter;
    private int r, g, b;

    public Cluster(Point p) {
        points = new ArrayList<>();
        oldCenter = new Point(0, 0);
        center = p;
        r = (int) (Math.random() * 256);
        g = (int) (Math.random() * 256);
        b = (int) (Math.random() * 256);
    }

    public void addPoint(Point p) {
        points.add(p);
    }

    public ArrayList<Point> getPoints() {
        return points;
    }

    public void clearPoints() {
        points.clear();
    }

    public Point getCenter() {
        return center;
    }

    public Point reCalculateCenter() {
        double xSum = 0, ySum = 0;
        for (Point p : points) {
            xSum += p.getX();
            ySum += p.getY();
        }
        xSum /= points.size();
        ySum /= points.size();
        Point p = new Point(xSum, ySum);
        oldCenter = center;
        center = p;
        return p;
    }

    public String toString() {
        return "Center: (" + center.getX() + ", " + center.getY() + ")\t" + "There are " + points.size() + " points in the cluster.";
    }
}
