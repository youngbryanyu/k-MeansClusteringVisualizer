import javax.swing.*;
import java.util.ArrayList;

public class SkinFilter implements PixelFilter {
    final short[][] kernel = {{1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}};
    private static short[][] out;
    private static short[][] out2;

    private int kernelWeight;

    // red: 186, green: 108, blue: 75
    private short red = 60;
    private short green = 70;
    private short blue = 75;

    private double THRESHOLD = 80;
    private static final int THRESHOLD2 = 254;

    private final int maxClusters = 10;
    private short[] reds, greens, blues;

    private ArrayList<Cluster> clusters;
    private ArrayList<Point> oldCenters;
    private ArrayList<Point> allPoints;

    private static double BAD_THRESHOLD = .3;
    private static double NUM_SDS = 3;

    private boolean firstLoop = true;

    public SkinFilter() {
        reds = new short[maxClusters];
        greens = new short[maxClusters];
        blues = new short[maxClusters];

        for (int i = 0; i < maxClusters; i++) {
            reds[i] = (short) (Math.random() * 256);
            greens[i] = (short) (Math.random() * 256);
            blues[i] = (short) (Math.random() * 256);
        }
    }

    @Override
    public int[] filter(int[] pixels, int width, int height) {
        int[][] pixels2d = PixelLib.convertTo2dArray(pixels, width, height);
        PixelLib.ColorComponents2d img = PixelLib.getColorComponents2d(pixels2d);

        kernelWeight = sumOf(kernel);
        if (out == null) {  // initialize to start, then re-use
            out = new short[height][width];
            out2 = new short[height][width];
        }

        performThreshold(img, out);
        performBlur(out, out2);
        performSecondThreshold(out2);

        oldCenters = new ArrayList<Point>();
        allPoints = getAllPoints(out2);

        int numClusters = 0;

        do {
            numClusters++;
            if (firstLoop || !areGoodClusters(clusters)) {
                clusters = new ArrayList<>();
                initializeClusters(numClusters);
            }

            do {
                for (Cluster c : clusters) {
                    c.clearPoints();
                }
                for (Point p : allPoints) {
                    addToClosestCluster(p, clusters);
                }
                oldCenters = new ArrayList<>();
                for (Cluster c : clusters) {
                    oldCenters.add(c.getCenter());
                    c.reCalculateCenter();
                }
            } while (isUnchanged(clusters, oldCenters));
        } while (!areGoodClusters(clusters) && numClusters < maxClusters);

        firstLoop = false;

        for (int r = 0; r < 480; r++) {
            for (int c = 0; c < 640; c++) {
                img.red[r][c] = 0;
                img.green[r][c] = 0;
                img.blue[r][c] = 0;
            }
        }

        for (Cluster cluster : clusters) {
            for (Point p : cluster.getPoints()) {
                int r = (int) p.getX();
                int c = (int) p.getY();
                img.red[r][c] = reds[clusters.indexOf(cluster)];
                img.green[r][c] = greens[clusters.indexOf(cluster)];
                img.blue[r][c] = blues[clusters.indexOf(cluster)];
            }
        }

        pixels = PixelLib.combineColorComponents(img);
        return pixels;
    }

    private ArrayList<Cluster> addOneCluster(ArrayList<Cluster> clusters) {
        int randomRow = (int) (Math.random() * out2.length);
        int randomCol = (int) (Math.random() * out2[0].length);
        Point p = new Point(randomRow, randomCol);
        Cluster c = new Cluster(p);

        clusters.add(c);
        return clusters;
    }

    private boolean areGoodClusters(ArrayList<Cluster> clusters) {
        for (Cluster c : clusters) {
            if (isBadCluster(c))
                return false;
        }
        return true;
    }

    private boolean isBadCluster(Cluster c) {
        double sd = getStandardDev(c);

        int numBadPoints = 0;

        for (Point p : c.getPoints()) {
            if (distance(p, c) > NUM_SDS * sd)
                numBadPoints++;
        }

        double average = (double) (numBadPoints) / c.getPoints().size();
        return average > BAD_THRESHOLD;
    }

    private double getStandardDev(Cluster c) {
        int numPoints = c.getPoints().size();
        double mean = 0;
        for (Point p : c.getPoints()) {
            mean += distance(p, c);
        }
        mean /= numPoints;

        double sd = 0;
        for (Point p : c.getPoints()) {
            sd += (distance(p, c) - mean) * (distance(p, c) - mean);
        }
        return Math.sqrt(sd / (numPoints - 1));
    }

    private boolean isUnchanged(ArrayList<Cluster> clusters, ArrayList<Point> oldCenters) {
        for (int i = 0; i < clusters.size(); i++) {
            if (!clusters.get(i).equals(oldCenters.get(i)))
                return false;
        }
        return true;
    }

    private void addToClosestCluster(Point p, ArrayList<Cluster> clusters) {
        Cluster closest = clusters.get(0);
        for (Cluster c : clusters) {
            if (distance(p, c) < distance(p, closest))
                closest = c;
        }
        closest.addPoint(p);
    }

    public double distance(Point p, Cluster c) {
        double dx = p.getX() - c.getCenter().getX();
        double dy = p.getY() - c.getCenter().getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    private void initializeClusters(int numClusters) {
        for (int i = 0; i < numClusters; i++) {
            int randomRow = (int) (Math.random() * out2.length);
            int randomCol = (int) (Math.random() * out2[0].length);
            Point p = new Point(randomRow, randomCol);
            Cluster c = new Cluster(p);

            clusters.add(c);
        }
    }

    private ArrayList<Point> getAllPoints(short[][] out2) {
        ArrayList<Point> points = new ArrayList<>();
        for (int r = 0; r < out2.length; r++) {
            for (int c = 0; c < out2[0].length; c++) {
                if (out2[r][c] != 0) {
                    Point p = new Point(r, c);
                    points.add(p);
                }
            }
        }
        return points;
    }

    private void performSecondThreshold(short[][] out2) {
        for (int r = 0; r < out2.length; r++) {
            for (int c = 0; c < out2[0].length; c++) {
                int dist = out2[r][c];
                if (dist > THRESHOLD2) {
                    out2[r][c] = 255;
                } else {
                    out2[r][c] = 0;
                }
            }
        }
    }

    private int sumOf(short[][] kernal) {
        int sum = 0;
        for (int i = 0; i < kernal.length; i++) {
            for (int j = 0; j < kernal[i].length; j++) {
                sum += kernal[i][j];
            }
        }
        if (sum == 0) return 1;
        return sum;
    }

    private void performBlur(short[][] out, short[][] out2) {
        for (int r = 0; r < out.length - kernel.length - 1; r++) {
            for (int c = 0; c < out[0].length - kernel.length - 1; c++) {
                int outputColor = calculateOutputFrom(r, c, out, kernel);
                out2[r][c] = (short) (outputColor / kernelWeight);
                if (out2[r][c] < 0) out2[r][c] = 0;
                if (out2[r][c] > 255) out2[r][c] = 255;
            }
        }
    }

    private int calculateOutputFrom(int r, int c, short[][] im, short[][] kernal) {
        int out = 0;
        for (int i = 0; i < kernal.length; i++) {
            for (int j = 0; j < kernal[i].length; j++) {
                out += im[r + i][c + j] * kernal[i][j];
            }
        }
        return out;
    }

    private void performThreshold(PixelLib.ColorComponents2d img, short[][] out) {
        for (int r = 0; r < out.length; r++) {
            for (int c = 0; c < out[0].length; c++) {
                double dist = distance(red, img.red[r][c], green, img.green[r][c], blue, img.blue[r][c]);
                if (dist > THRESHOLD) {
                    out[r][c] = 0;
                } else {
                    out[r][c] = 255;
                }
            }
        }
    }

    public double distance(short r1, short r2, short g1, short g2, short b1, short b2) {
        int dr = r2 - r1;
        int dg = g2 - g1;
        int db = b2 - b1;
        return Math.sqrt(dr * dr + dg * dg + db * db);
    }
}




