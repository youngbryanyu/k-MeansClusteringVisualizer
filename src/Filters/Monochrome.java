import javax.swing.*;

public class Monochrome implements PixelFilter{
    int response;
    public Monochrome(){
        response = Integer.parseInt(JOptionPane.showInputDialog("Enter a threshold"));
    }
    public int[] filter(int[] pixels, int width, int height) {
        short[] bwpixels = PixelLib.convertToShortGreyscale(pixels);

        for (int i = 0; i < bwpixels.length; i++) {
            if(bwpixels[i] > response)
                bwpixels[i] = 255;
            else
                bwpixels[i] = 0;
        }

        PixelLib.fill1dArray(bwpixels, pixels);
        return pixels;
    }
}
