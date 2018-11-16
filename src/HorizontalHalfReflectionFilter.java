public class HorizontalHalfReflectionFilter implements PixelFilter {

    public int[] filter(int[] pixels, int width, int height) {
        short[] bwpixels = PixelLib.convertToShortGreyscale(pixels);
        short[][] img = PixelLib.convertTo2dArray(bwpixels, width, height);

        int lastColIndex = img[0].length - 1;
        for (int r = 0; r < img.length; r++) {
            for (int c = 0; c < img[r].length / 2; c++) {
                short temp = img[r][c];
                img[r][c] = img[r][lastColIndex - c];
                img[r][lastColIndex] = temp;
            }
        }

        PixelLib.fill1dArray(img, pixels);
        return pixels;
    }
}