public class FourWayReflection implements PixelFilter {
    public int[] filter(int[] pixels, int width, int height) {
        short[] bwpixels = PixelLib.convertToShortGreyscale(pixels);
        short[][] img = PixelLib.convertTo2dArray(bwpixels, width, height);

        int lastRowIndex = img.length - 1;
        int lastColIndex = img[0].length - 1;
        for (int r = 0; r < img.length / 2; r++) {
            for (int c = 0; c < img[0].length / 2; c++) {
                img[lastRowIndex - r][lastColIndex - c] = img[r][c];
                img[lastRowIndex - r][c] = img[r][c];
                img[r][lastColIndex - c] = img[r][c];
            }
        }

        PixelLib.fill1dArray(img, pixels);
        return pixels;
    }
}
