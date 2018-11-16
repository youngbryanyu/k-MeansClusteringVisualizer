public class Rotate180DegreesFilter implements PixelFilter {
    public int[] filter(int[] pixels, int width, int height) {
        short[] bwpixels = PixelLib.convertToShortGreyscale(pixels);
        short[][] img = PixelLib.convertTo2dArray(bwpixels, width, height);

        int lastRowIndex = img.length - 1;
        int lastColIndex = img[0].length - 1;
        for (int r = 0; r < img.length / 2; r++) {
            for (int c = 0; c < img[r].length; c++) {
                short temp = img[r][c];
                img[r][c] = img[lastRowIndex - r][lastColIndex - c];
                img[lastRowIndex - r][lastColIndex - c] = temp;
            }
        }

        PixelLib.fill1dArray(img, pixels);
        return pixels;
    }
}
