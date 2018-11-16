public class PixelateFilter implements PixelFilter {

    public int[] filter(int[] pixels, int width, int height) {
        short[] bwpixels = PixelLib.convertToShortGreyscale(pixels);
        short[][] img = PixelLib.convertTo2dArray(bwpixels, width, height);

        int lastRowIndex = img.length - 1;
        int lastColIndex = img[0].length - 1;
        for (int r = 1; r < img.length; r += 3) {
            for (int c = 1; c < img[0].length; c += 3) {
                for (int i = r - 1; i < r + 2; i++) {
                    for (int j = c - 1; j < c + 2; j++) {
                        img[i][j] = img[r][c];
                    }
                }
            }
        }

        PixelLib.fill1dArray(img, pixels);
        return pixels;
    }
}
