import javax.swing.*;

public class Polychrome implements PixelFilter {
    int response;

    public Polychrome() {
        response = Integer.parseInt(JOptionPane.showInputDialog("Enter a threshold"));
    }

    public int[] filter(int[] pixels, int width, int height) {
        short[] bwpixels = PixelLib.convertToShortGreyscale(pixels);

        for (int i = 0; i < bwpixels.length; i++) {
            for (int j = 0; j < 256; j += 255 / response) {
                if (j > bwpixels[i]) {
                    bwpixels[i] = (short) (j - .5 * 255 / response);
                    break;
                }
            }
        }

        PixelLib.fill1dArray(bwpixels, pixels);
        return pixels;
    }
}

