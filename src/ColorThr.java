import javax.swing.*;

public class ColorThr implements PixelFilter {

    int r, g, b, dist;

    public ColorThr() {
        r = Integer.parseInt(JOptionPane.showInputDialog("Enter your target red values"));
        g = Integer.parseInt(JOptionPane.showInputDialog("Enter your target green values"));
        b = Integer.parseInt(JOptionPane.showInputDialog("Enter your target blue values"));
        dist = Integer.parseInt(JOptionPane.showInputDialog("Enter the threshold distance"));
    }

    public int[] filter(int[] pixels, int width, int height) {
        PixelLib.ColorComponents2d vals = PixelLib.getColorComponents2d(pixels, width, height);

        for (int r = 0; r < height; r++) {
            for (int c = 0; c < width; c++) {
                double dr = vals.red[r][c] - r;
                double dg = vals.green[r][c] - g;
                double db = vals.blue[r][c] - b;
                int distance = (int) Math.sqrt(dr * dr + dg * dg + db * db);
                vals.red[r][c] = 255;
                vals.green[r][c] = 255;
                vals.blue[r][c] = 255;
                if (distance > dist) {
                    vals.red[r][c] = 0;
                    vals.green[r][c] = 0;
                    vals.blue[r][c] = 0;
                }
            }
        }

        pixels = PixelLib.combineColorComponents(vals);
        return pixels;
    }
}
