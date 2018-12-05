
import javax.swing.*;
import java.util.ArrayList;

public class SkinFilter implements PixelFilter {
    final short[][] kernel = {{1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}, {1, 1, 1, 1, 1}};
    private static short[][] out;
    private static short[][] out2;
    private int kernelWeight;
    private short red = 186;
    private short green = 108;
    private short blue = 73;
    private double THRESHOLD = 80;
    private static final int THRESHOLD2 = 254;
    private int numClusters;
    private Cluster[] clusters;

    public SkinFilter() {
        numClusters = Integer.parseInt(JOptionPane.showInputDialog("enter a number"));
        initializeClusters();
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

        ArrayList<Point> allPoints = getAllPoints(out2);

        assignPoints(allPoints);

        for (int i = 0; i < 10; i++) {
            recalculateCenters();
            assignPoints(allPoints);
        }

        colorImageWithClusters(img);
        pixels = PixelLib.combineColorComponents(img);
        // PixelLib.fill1dArray(out2, pixels);
        return pixels;
    }

    private void colorImageWithClusters(PixelLib.ColorComponents2d img) {
        for (int r = 0; r < 480; r++) {
            for (int c = 0; c < 640; c++) {
                img.red[r][c] = 0;
                img.green[r][c] = 0;
                img.blue[r][c] = 0;
            }
        }
        for (int i = 0; i < clusters.length; i++) {
            ArrayList<Point> points = clusters[i].getPoints();
            for (Point p : points) {
                int r = (int) p.getY();
                int c = (int) p.getX();
                img.red[r][c] = (short) clusters[i].getRed();
                img.green[r][c] = (short) clusters[i].getBlue();
                img.blue[r][c] = (short) clusters[i].getGreen();
            }
        }
    }

    private void recalculateCenters() {
        for (int i = 0; i < clusters.length; i++) {
            clusters[i].reCalculateCenter();
        }
    }

    private void assignPoints(ArrayList<Point> allPoints) {
        for (int i = 0; i < clusters.length; i++) {
            clusters[i].clearPoints();
        }
        for (Point p : allPoints) {
            int clusterNum = 0;
            double closestDist = Integer.MAX_VALUE;
            for (int i = 0; i < numClusters; i++) {
                Cluster c = clusters[i];
                if (distance(p, c) < closestDist) {
                    closestDist = distance(p, c);
                    clusterNum = i;
                }
            }
            clusters[clusterNum].addPoint(p);
        }
    }

    private ArrayList<Point> getAllPoints(short[][] out2) {
        ArrayList<Point> points = new ArrayList<>();
        for (int r = 0; r < out2.length; r++) {
            for (int c = 0; c < out2[0].length; c++) {
                if (out2[r][c] == 255) {
                    Point p = new Point(c, r);
                    points.add(p);
                }
            }
        }
        return points;
    }

    private void initializeClusters() {
        clusters = new Cluster[numClusters];
        for (int i = 0; i < clusters.length; i++) {
            clusters[i] = new Cluster();
        }
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

    public double distance(Point p, Cluster c) {
        double dx = p.getX() - c.getCenter().getX();
        double dy = p.getY() - c.getCenter().getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
}




