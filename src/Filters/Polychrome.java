import javax.swing.*;

public class Polychrome implements PixelFilter {
    int response;
    int intervalSize;

    public Polychrome() {
        response = Integer.parseInt(JOptionPane.showInputDialog("Enter a threshold"));
        intervalSize = 255 / response;
    }

    public int[] filter(int[] pixels, int width, int height) {
        short[] bwpixels = PixelLib.convertToShortGreyscale(pixels);

        for (int i = 0; i < bwpixels.length; i++) {
            bwpixels[i] = (short) ((bwpixels[i] / intervalSize) * intervalSize - intervalSize / 2);
        }

        PixelLib.fill1dArray(bwpixels, pixels);
        return pixels;
    }
}
